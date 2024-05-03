import React, { useRef, useEffect, useState } from "react";
import { useSelector } from 'react-redux';
import "./MainMap.css";
import Button from 'react-bootstrap/Button';
import Accordion from 'react-bootstrap/Accordion';
import axios from 'axios';
import '../Mock/Mock'
//引入ol相关
import 'ol/ol.css';
import Map from 'ol/Map';
import View from 'ol/View';
import TileLayer from 'ol/layer/Tile';
import XYZ from 'ol/source/XYZ';
import { fromLonLat } from 'ol/proj';
import VectorLayer from 'ol/layer/Vector';
import VectorSource from 'ol/source/Vector';
import Feature from 'ol/Feature';
import Point from 'ol/geom/Point';
import LineString from 'ol/geom/LineString';
import { Style, Fill, Stroke, Circle } from 'ol/style';
import { Draw, Modify, Snap } from 'ol/interaction';
import { click } from "ol/events/condition";
// import LayerSwitcher from 'ol-layerswitcher';
import LayerSwitcherImage from 'ol-ext/control/LayerSwitcherImage';

// 子页面
import FunctionMenu from './FunctionMenu/FunctionMenu';
import GBselectResult from './GBselectResult/GBselectResult'
import GBpropertyDetail from './GBpropertyDetail/GBpropertyDetail'
import RouteSearch from "./RouteSearch/RouteSearch";
// 引入打印
import html2canvas from 'html2canvas';
import jsPDF from 'jspdf';




const MainMap = () => {
    // 用来获取组件开关 
  const showGBselectResult = useSelector(state => state.Maps.showGBselectResult);
    // mapbox 计算的经纬度坐标 
  const addressList = useSelector(state => state.Maps.addressList);
    // 获取画路线开关
  const fetchNaviInfoTrigger = useSelector(state => state.Maps.fetchNaviInfoTrigger);
  // 获取routes 
  const mapRef = useRef(null);
  const [urlMap, setUrlMap] = useState(null);



  useEffect(() => {
   
    const NAME = 'Alice';   //模拟登陆用户     Alice    Bob    Tom
    

    // ***获取地图基础层
    axios.post('api/FetchMap/', { name: NAME })
      .then(response => { 
        let res = null
        res = JSON.parse(JSON.stringify(response.data));
        console.log("res url" , res);
        setUrlMap(res)
      })
      .catch(error => {
        console.error('Error fetching data:', error);
      });
    
   const map = new Map({
      target: mapRef.current,
      //  图层1---地图基础层 
      layers: [
        //  openlayer需要多种不同类型的图层 TileLayer（瓦片图层）、VectorLayer（矢量图层）
        // 创建一个瓦片层（用于做一个底层）
        new TileLayer({
          source: new XYZ({
            title: "baseMap",
            url: urlMap,
            crossOrigin:'anonymous',
          }),
        }),
        new TileLayer({
          source: new XYZ({
           title: "trafficMap",
          url: 'https://api.mapbox.com/styles/v1/mapbox/traffic-night-v2/tiles/{z}/{x}/{y}?access_token=pk.eyJ1IjoiZGFpamlhbmciLCJhIjoiY2x1cm51NDRxMDlheDJpbzk2ajhzN3p1bSJ9._Jjt7AubEPM0RCoDCM4NTg' ,
          crossOrigin: 'anonymous',
        }),
      }),
      ],
      
      // 在地图对象的view属性中配置地图的视图。视图包括地图的中心点、缩放级别、投影方式等信息。
      // 2 定义视图
      view: new View({
        center: fromLonLat([139.77313296829274,35.69875754137042]),
        zoom: 17,
      }),
    });

    const layerSwitcher = new LayerSwitcherImage();
    map.addControl(layerSwitcher)
        
    // 图层2----矢量存储在数据库中图层集【循环创建】     
    axios.post('api/FetchRoutes/', { name: NAME })
      .then(response => {
        let routes = JSON.parse(JSON.stringify(response.data));
        console.log("routes",routes);
        if (routes !== null) {
          routes.forEach((route, index) => {
            let save_Source = new VectorSource();
              // 存储点创建
            route.marker.forEach(coord => {
              let Fcoord = fromLonLat(coord) 
              let markerFeature = new Feature({
                geometry: new Point(Fcoord),
              });
              
              save_Source.addFeature(markerFeature);
               });
              // 存储线创建
            let lineCoords = route.line.map(coord => fromLonLat(coord));
            let lineFeature = new Feature({
                geometry: new LineString(lineCoords),
            });
            save_Source.addFeature(lineFeature);
              // 存储层创建 
            let save_Layer = new VectorLayer({
              title: `route${index + 1}`,
              source: save_Source,
              style: [
                // 点的样式
                new Style({
                  image: new Circle({
                    radius: 6,
                    fill: new Fill({ color: 'red' }),
                    stroke: new Stroke({ color: 'white', width: 2 }),
                  }),
                }),
                // 线的样式
                new Style({
                  stroke: new Stroke({
                    color: 'blue',
                    width: 4,      
                  }),
                }),
              ],
            });
              map.addLayer(save_Layer);    
            
          })
          
        
        }

      })
      .catch(error => {
        console.error('获取信息+创建图层失败', error);
      });

// 图层3--- 用户自定义路径图层集
    const user_edit_vectorSource = new VectorSource();
  // 创建点的 Feature 并添加到 VectorSource
    addressList.forEach(coord => {
        const Fcoord = fromLonLat(coord) 
        const markerFeature = new Feature({
          geometry: new Point(Fcoord),
        });
        user_edit_vectorSource.addFeature(markerFeature);
    });
    //
  if (fetchNaviInfoTrigger) {
    axios.post('api/ACCESSTOKEN/', { name: NAME })
      .then(response => {
        let MAPBOX_ACCESS_TOKEN = JSON.parse(JSON.stringify(response.data));
        const coordinates = addressList.map(coord => `${coord[0]},${coord[1]}`).join(';');    
        fetch(`https://api.mapbox.com/directions/v5/mapbox/driving/${coordinates}?steps=true&geometries=geojson&access_token=${MAPBOX_ACCESS_TOKEN}`)
        .then(response => response.json())
        .then(data => {
          const route = data.routes[0].geometry.coordinates;
          const lineCoords_user = route.map(coord => fromLonLat(coord));
          const lineFeature_user = new Feature({
            geometry: new LineString(lineCoords_user),
          });
          user_edit_vectorSource.addFeature(lineFeature_user);
        });
      })
      .catch(error => {
        console.error('获取TOKEN失败', error);
      });
  }
  // 创建一个 VectorLayer，将 VectorSource 添加到其中
  const edit_Layer = new VectorLayer({
    title: "Edit_route",
    source: user_edit_vectorSource,
    style: [
      // 点的样式
      new Style({
        image: new Circle({
          radius: 6,
          fill: new Fill({ color: 'red' }),
          stroke: new Stroke({ color: 'white', width: 2 }),
        }),
      }),
      // 线的样式
      new Style({
        stroke: new Stroke({
          color: 'blue', 
          width: 4,      
        }),
      }),
    ],
  });
  const modifyInteraction = new Modify({
        source: user_edit_vectorSource,
  });
  map.addInteraction(modifyInteraction);
  map.addLayer(edit_Layer)
    

    // 销毁地图实例
    return () => {
      map.setTarget(null);
    };
  }, [addressList, fetchNaviInfoTrigger,urlMap]);
  
   

//*** 打印地图
  const handlePrintPDF = () => {
    // 获取地图容器元素 有点表示使用选择器
    const mapContainer = document.querySelector('.map-container');
// 越高图像会往左移
    const x = 220   
// 越低图像会像下移
    const y = 50
    const width = 1220
    const height= 704

    
    const options = {
      backgroundColor: '#061b48', // 设置 canvas 背景颜色，防止某些图片透明
      useCORS: true, // 允许跨域请求
    };
    // 将地图容器转换为 Canvas
    html2canvas(mapContainer,
      {
         x, // 截取区域的左上角 x 坐标
        y, // 截取区域的左上角 y 坐标
        width, // 截取区域的宽度
        height // 截取区域的高度
      }
      ,options).then(canvas => {
      // 创建 PDF 对象
      const pdf = new jsPDF('landscape', 'px', [canvas.width, canvas.height]);
      // 将 Canvas 添加到 PDF
      pdf.addImage(canvas.toDataURL('image/png'), 'PNG', 0, 0, canvas.width, canvas.height);

      // 下载 PDF 文件
      pdf.save('map.pdf');
    });
  }
// ***********************

  return (
    <div>
      {/* 地图窗口 */}
      <div className="map-container" ref={mapRef}  >
      </div>
      {/* 固定浮动窗口 */}
      {showGBselectResult && <div className="fixed-window">
        <GBselectResult />
      </div>}
      {/* 浮动窗口 */}
      <GBpropertyDetail />
      <RouteSearch className="RouteSearch" />
      
      {/* header */}

      <div className="header" style={{ paddingLeft: '45px', paddingTop: '5px', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <h4 style={{ margin: 0 }}>地図アプリケーション v3.0</h4>
        <Button variant="light" size="sm" style={{marginRight:'10px'}} onClick={handlePrintPDF}>Print to PDF</Button>
      </div>
      

      {/* 左主要菜单栏 */}

      
      <Accordion className="left-sider" style={{ width:'225px', margin:'0px',  padding: '0px',}}>
        <Accordion.Item eventKey="0" >
          <Accordion.Header className="accordionHeader">機能メニュー</Accordion.Header>
          <Accordion.Body style={{  maxHeight: '625px', overflowY: 'auto',margin:'0px',  padding: '0px'}} >
            <FunctionMenu ></FunctionMenu>
           </Accordion.Body>
         </Accordion.Item>
      </Accordion>
      
      {/* footer */}
      
      <div className="footer"></div>
    </div>
  );
};

export default MainMap;
