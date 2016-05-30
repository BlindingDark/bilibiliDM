# bilibili 弹幕姬/点歌姬

简易的自制 bilibili 点歌姬  
###注意事项
使用了 WMP 作为播放器，非 WINDOWS 平台可以使用对应的流媒体播放器。

###启动
控制台运行 main.Launcher 主函数;
new ConnectServer(直播房间ID, 用户ID);
###点歌格式
点歌==歌名  
点歌 歌名  
{music:歌名}  

###屏蔽列表
在项目根目录 illegal_music_list 中填写屏蔽的歌曲名，换行分割。

###无人点歌时的默认播放列表
在项目根目录 default_music_list 中填写要随机的歌曲名，换行分割。

###感谢
https://github.com/stleary/JSON-java  
https://github.com/mawenbao/music-api-server  
http://www.lyyyuna.com/2016/03/14/bilibili-danmu01/  

###协议
**WTFPL**
