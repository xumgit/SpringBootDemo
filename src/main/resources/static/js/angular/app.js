/**
 * 
 */

 var angularDataApp = angular.module('angularDataApp', ['ngAnimate','ngRoute','ngCookies'])
 .constant('Configure', {
    URLBase:'/',
    uploadUrlBase:'http://localhost:8080/',
    ImgUrl:'/images/touxiang.jpg',
    Language:{
        "lengthMenu": "row",
        "paginate": {
            "previous": "pre",
            "next": "nex",

        },
        "processing": "loading..."
    },
    PageLength: 15,
    DateTimeConfigure:{
    	 locale: 'zh-cn',
         showTodayButton: true
    }
})