import Mock from 'mockjs'

// 自动生成的api数据

Mock.mock('/api/test', 'get', {
  'data|20': [
    {
      'id|+1': 1,
      studentId: function () {
        // 为了好看，给前面数据添加两个0
        return ('00' + this.id).slice(-3)
      },
      name: '@name',
      'gender|1': ['Male', 'Female'],
      'age|15-75': 1
    }
  ]
})

// 可删除： 测试vscode 内部github工具
