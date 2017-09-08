# MiuiVideoProgressBar
仿小米视频加载控件
效果如下:  
![](https://raw.githubusercontent.com/houtrry/MiuiVideoProgressBar/master/img/miuiviedo.gif)  

## 实现思路  
简单讲一下实现思路.  
首先, 观察加载动画, 我们可以得到如下信息:  
![](https://raw.githubusercontent.com/houtrry/MiuiVideoProgressBar/master/img/%E5%88%86%E8%A7%A3%E5%9B%BE.png)
1. 加载动画由四个三角形组成.  
2. 动画由渐增和渐减两个状态.
3. 每个三角形在渐增和渐减两个状态下, 顶点不同.
4. 渐增状态下的变化顺序和渐减状态下, 四个三角形的变化顺序不同.
5. 
