# MiuiVideoProgressBar
仿小米视频加载控件
效果如下:  
![](https://raw.githubusercontent.com/houtrry/MiuiVideoProgressBar/master/img/miuiviedo.gif)  

## 实现  
### 观察动画 
首先, 观察加载动画, 我们可以得到如下信息:  
![](https://raw.githubusercontent.com/houtrry/MiuiVideoProgressBar/master/img/%E5%88%86%E8%A7%A3%E5%9B%BE.png)
1. 加载动画由四个三角形组成, 而这四个三角形总共由6个点组成.  
2. 动画由渐增和渐减两个状态.  
3. 每个三角形在渐增和渐减两个状态下, 顶点不同.  
4. 渐增状态下的变化顺序和渐减状态下, 四个三角形的变化顺序不同.  
	渐增时, 变化顺序是1-2-3-4,   
	渐减时, 变化顺序是4-2-3-1.    

### 实现思路
1. 首先, 如何画一个三角形? 只要知道三角形的三个点. 就可以画出三角形.  
```
  Path path = new Path();  
  path.moveTo(顶点);  
  path.lineTo(第二个点);  
  path.lineTo(第三个点);  
  path.close();  
  canvas.drawPath(path, paint);  
```  
2. 那么, 现在的问题就是来计算三角形的三个点了. 在已知六个点的坐标和当前进度progress的情况下, 计算每个三角形的当前位置也只是个很简单的数学几何问题.  
3. 这里有个问题就是渐增和渐减状态下, 四个三角形的变化顺序不同. 其实仔细分析的话, 需要处理的只是三角形2和三角形3. 在渐减的情况下, 要调换一下变化顺序. 这里, 我的处理方式是, 处理progress. 代码中, progress的值是一个0 ~ 4.0的值, progress变换过程中, 我们要分别计算每个三角形的progress(范围0 ~ 1.0, 我们称之为currentProgress),  
对于第二个三角形,  
渐增时, currentProgress = progress - 1, 渐减时, currentProgress = progress - 2.   
同理, 对于第三个三角形,   
渐增时, currentProgress = progress - 2, 渐减时, currentProgress = progress - 1.  
通过这样的处理, 就可以达到修改变化顺序的目的.
