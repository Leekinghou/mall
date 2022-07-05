# SpringBoot 

# Controller层
## 路由传参的形式
演示各种传参形式  
### RestController
1. 是一个controller    
2. 具备restful的前后端分离风格

### RequestMapping
为多个请求路由加上统一的前缀   
### 各种注解及形式
```java
@RestController // RestController：1. 是一个controller。 2. 具备restful的前后端分离风格
@RequestMapping("/prefix") // RequestMapping: 为多个请求路由加上统一的前缀
public class ParaController {

    @GetMapping({"/firstrequest"})
    public String firstRequest() {
        return "第一个接口API";
    }

    // RequestParam: 从请求的信息中找到参数
    @GetMapping({"/requestpara"})
    public String requestPara(@RequestParam Integer num) {
        return "para from request: " + num;
    }

    // PathVariable: 从请求的url路径中找到参数
    @GetMapping({"/param/{num}"})
    public String pathParam(@PathVariable Integer num) {
        return "para from path: " + num;
    }

    // 多个路由对应一个方法
    @GetMapping({"multiurl1", "multiurl2"})
    public String multiurl(@RequestParam Integer num) {
        return "para from request: " + num;
    }

    // 参数可以为空，并设定默认值
    @GetMapping({"required"})
    public String required(@RequestParam(required = false, defaultValue = "0") Integer num) {
        return "para from request: " + num;
    } 
}
```

# Java异常体系
![](https://image-20220620.oss-cn-guangzhou.aliyuncs.com/image/20220630180013.png)


# 参数校验
1. 第一条相当于总开关
![](https://image-20220620.oss-cn-guangzhou.aliyuncs.com/image/1656769774863.png)


# 商品分类模块总结
![](https://image-20220620.oss-cn-guangzhou.aliyuncs.com/image/20220705113037.png)
