# bilibili 弹幕姬/点歌姬

简易的自制 bilibili 点歌姬  

###特点
选取下一个歌曲时，不是在歌曲结束瞬间抓弹幕选取下一首  
而是在最近 100 条(默认)弹幕中随机选取

###主要功能
- 弹幕点歌（废话）
- 没有人点歌时播放默认播放列表
- 屏蔽系统。不同歌曲链接，只要网易云的名称一样，只需填写一个屏蔽项。（看心情加模糊匹配功能）
- 断线自动重连。（重连次数过多会导致内存爆炸，看心情修复，原因是使用了递归来重连。）

###注意事项
默认使用了 Windows Media Player 作为播放器，
确保 C:/Program Files (x86)/Windows Media Player/wmplayer.exe 存在  
非 WINDOWS 平台在 tools.Mp3Player 类中切换到 Java 版本的音乐播放器（但是不是很稳定）。

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
http://www.javazoom.net/javalayer/javalayer.html

###协议
**WTFPL**
