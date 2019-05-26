/**
 * @description 菜单数据
 */

export default [
    {
      name: '录播任务',icon:'fa fa-video-camera',
      subMenu: [
        {name: '节目单', link: 'program'},
        {name: '创建节目', link: 'create-program'}
      ]
    },
    {
      name: '课程资源',icon:'fa fa-calculator',
      subMenu: [
        {name: '课程列表', link: 'course-list'},
        {name: '课堂视频／教辅文件', link: 'course-video'},
        {name: '教材列表', link: 'textbook-list'}
      ]
    },
    {
      name: '帐户管理',icon:'fa  fa-user fa-lg',
      subMenu: [
        {name: '帐户列表', link: 'account-list'},
        {name: '角色管理', link: 'role'},
        {name: '学校架构', link: 'school-framework'},
        {name: '我的帐户', link: 'my-account'}
      ]
    }
]
