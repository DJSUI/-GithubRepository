import Mock from 'mockjs';

// Mock.setup({timeout:'1000'})

const tokenList = [
  { name: 'Alice', rolToken: 'admin' },
  { name: 'Bob', rolToken: 'user' },
  { name: 'Tom', rolToken: 'guest' },
];


// API1--- 用于返回模拟保存在数据库中的信息 
Mock.mock('api/FetchRoutes/', 'post', (options) => {
   const { name } = JSON.parse(options.body); 
  
   const routes_admin = [
      {
       marker: [[139.773755, 35.69869], [139.771127, 35.691903]],
       line: [
      [139.773755, 35.69869],
      [139.77376, 35.699025],
      [139.773845, 35.699076],
      [139.773982, 35.69903],
      [139.773991, 35.698767],
      [139.7742, 35.698737],
      [139.774347, 35.699438],
      [139.771466, 35.69965],
      [139.771254, 35.697633],
      [139.770331, 35.695291],
      [139.771628, 35.69204],
      [139.771537, 35.692014],
      [139.771169, 35.6929],
      [139.770589, 35.691757],
      [139.771127, 35.691903]
      ]
     },
     {
       marker:[ [139.7853, 35.697272],  [139.793862, 35.695631]],
       line:[
    [139.7853, 35.697272],
    [139.784681, 35.697321],
    [139.784657, 35.697323],
    [139.784618, 35.697326],
    [139.784619, 35.697338],
    [139.78463, 35.697468],
    [139.784632, 35.697482],
    [139.784668, 35.697479],
    [139.785132, 35.697442],
    [139.793947, 35.694393],
    [139.793942, 35.694463],
    [139.793911, 35.694884],
    [139.793891, 35.695216],
    [139.793865, 35.695585],
    [139.793862, 35.695631]
     ]
     }
                    
    ];
  
  const routes_user = [
     {
       marker:[ [139.7853, 35.697272],  [139.793862, 35.695631]],
       line:[
    [139.7853, 35.697272],
    [139.784681, 35.697321],
    [139.784657, 35.697323],
    [139.784618, 35.697326],
    [139.784619, 35.697338],
    [139.78463, 35.697468],
    [139.784632, 35.697482],
    [139.784668, 35.697479],
    [139.785132, 35.697442],
    [139.793947, 35.694393],
    [139.793942, 35.694463],
    [139.793911, 35.694884],
    [139.793891, 35.695216],
    [139.793865, 35.695585],
    [139.793862, 35.695631]
     ]
     }
                    
    ];

  const user = tokenList.find(tokenObj => tokenObj.name === name);

  if (user.rolToken === 'admin') {

       return Mock.mock(routes_admin);

    // return Mock.mock(user.rolToken);
  } else if (user.rolToken === 'user') {
      return Mock.mock(routes_user);
  }else{
 
     return Mock.mock(null);
  }
});

// API2----用于返回API+Token 
Mock.mock('api/FetchMap/', 'post', (options) => {
  const { name } = JSON.parse(options.body); 
  const MAPBOX_ACCESS_TOKEN = 'pk.eyJ1IjoiZGFpamlhbmciLCJhIjoiY2x1cm51NDRxMDlheDJpbzk2ajhzN3p1bSJ9._Jjt7AubEPM0RCoDCM4NTg';

  const user = tokenList.find(tokenObj => tokenObj.name === name);

  if (user.rolToken === 'admin') {

       return Mock.mock(`https://api.mapbox.com/styles/v1/mapbox/streets-v11/tiles/256/{z}/{x}/{y}@2x?access_token=${MAPBOX_ACCESS_TOKEN}`);


  } else if (user.rolToken === 'user') {
      return Mock.mock(`https://api.mapbox.com/styles/v1/mapbox/streets-v11/tiles/256/{z}/{x}/{y}@2x?access_token=${MAPBOX_ACCESS_TOKEN}`);
  }else{
 
     return Mock.mock(null);
  }
});

 // API3-----用于返回TOKEN

Mock.mock('api/ACCESSTOKEN/', 'post', (options) => {
  const { name } = JSON.parse(options.body); 
  const MAPBOX_ACCESS_TOKEN = 'pk.eyJ1IjoiZGFpamlhbmciLCJhIjoiY2x1cm51NDRxMDlheDJpbzk2ajhzN3p1bSJ9._Jjt7AubEPM0RCoDCM4NTg';

  const user = tokenList.find(tokenObj => tokenObj.name === name);

  if (user.rolToken === 'admin' || 'user') {

       return Mock.mock(MAPBOX_ACCESS_TOKEN);
  }else{
 
     return Mock.mock(null);
  }
});

