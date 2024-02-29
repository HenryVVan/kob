### MySQL

#### MySQL服务

> 默认开机自动启动

- 启动：`net start mysql`
- 关闭：`net stop mysql`

#### MySQL常用操作

> ​	连接用户名root，密码wh1234的数据库服务

![image-20231211201234539](https://img2023.cnblogs.com/blog/3313059/202312/3313059-20231211201236656-367033352.png)

> ​	列出所有数据库

![image-20231211201244528](https://img2023.cnblogs.com/blog/3313059/202312/3313059-20231211201246496-493167223.png) 

> 创建数据库

![image-20231211201412267](https://img2023.cnblogs.com/blog/3313059/202312/3313059-20231211201414316-785034162.png) 

> 创建数据表

![image-20231211201354599](https://img2023.cnblogs.com/blog/3313059/202312/3313059-20231211201356527-461099293.png)

> 向数据表中插入数据

![image-20231211201648107](https://img2023.cnblogs.com/blog/3313059/202312/3313059-20231211201650100-1628804594.png) 

> ​	查询表中所有数据

![image-20231211201702438](https://img2023.cnblogs.com/blog/3313059/202312/3313059-20231211201704388-254393042.png) 

#### IDEA相关数据库配置

![image-20231211202009298](https://img2023.cnblogs.com/blog/3313059/202312/3313059-20231211202012292-764736472.png)

![image-20231211202217794](https://img2023.cnblogs.com/blog/3313059/202312/3313059-20231211202219952-457508084.png)

### 配置SpringBoot

#### 添加依赖

在[Maven仓库地址](https://gitee.com/link?target=https%3A%2F%2Fmvnrepository.com%2F)中搜索相关依赖

然后在`pom.xml`文件中添加依赖：

- `Spring Boot Starter JDBC`

- `Project Lombok`

  自动资源管理、自动生成 getter、setter、equals、hashCode 和 toString 等

- `MySQL Connector/J`

- `mybatis-plus-boot-starter`

- `mybatis-plus-generator`

  [Mybatis-Plus官网](https://gitee.com/link?target=https%3A%2F%2Fbaomidou.com%2F)

- `spring-boot-starter-security`（暂时不装）

- `jjwt-api`（暂时不装）

将复制来的依赖，粘贴至pom.xml


然后，需要重新加载maven项目

![image-20231211205114580](https://img2023.cnblogs.com/blog/3313059/202312/3313059-20231211205116760-1133873799.png) 

如果加载不成功，需要清理缓存

![image-20231211204649108](https://img2023.cnblogs.com/blog/3313059/202312/3313059-20231211204651538-350032083.png)

#### 数据库配置

在`application.properties`中添加数据库配置：

```
spring.datasource.username=root
spring.datasource.password=wh1234
spring.datasource.url=jdbc:mysql://localhost:3306/kob?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

![image-20231229162315516](https://img2023.cnblogs.com/blog/3313059/202312/3313059-20231229162317089-1767249644.png)

此时运行项目即可

#### SpringBoot中的常用模块

- pojo层：将数据库中的表对应成`Java`中的类

  (pojo里的一个User对象，对应数据库中的一行数据)

- mapper层（DAO层）：将pojp层对类（Class）的操作（CRUD），映射成sql语句
  - create -> insert
  - retrieve -> select

- service层：写具体的业务逻辑，组合使用mapper中的操作
- controller层：负责请求转发，接受前端页面传过来的参数，并传给相应的service处理

##### pojo层

首先创建`com/kob/backend/pojo`包，然后在其中创建`User.java`

负责实现与`User`表相对应的`User`类

![image-20231229171633149](https://img2023.cnblogs.com/blog/3313059/202312/3313059-20231229171634532-1333072696.png)

关键在于三个注解，添加注解后，IDEA会自动将有参、无参构造函数补全，这点可以在target中验证

![image-20231229174125966](https://img2023.cnblogs.com/blog/3313059/202312/3313059-20231229174127737-373322121.png)

##### mapper层

首先创建`com/kob/backend/mapper`包，然后在其中创建`UserMapper.java`

添加`@Mapper`注解，并继承mybatisplus的BaseMapper，传入`<User>`，目的是将pojo层的User中的CRUD操作映射成sql语句

![image-20240105154852367](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105154852943-1741725443.png)

##### controller层

为了方便调试，在当前阶段，暂时将`service`和`controller`写在一起（在后期具体业务中需要分开，用controller去调用service中的接口）

创建`com/kob/backend/controller`包

然后针对`User`表创建测试使用的`/user/UserController.java`

添加`@RestController`注解

在这里我们可以实现与`User`表相关的业务逻辑（正常应该在`service`层 这里为了方便调试 暂时写在一块了）

`@RequestMapping `将所有请求类型全部接收过来

- 如果只处理`post`类型的请求`@PostMapping`
- 如果只处理`get`类型的请求`@GetMapping`

1）实现查询当前所有用户

在controller中调用数据库的接口

首先引入刚刚定义的UserMapper接口

```
@Autowired
UserMapper userMapper;
```

userMapper是mybatisplus实现的

![image-20240105155903913](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105155904264-1047631243.png) 

继承于BaseMapper

![image-20240105155943560](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105155943931-435677277.png) 

可以通过[Mybatis-Plus官网](https://gitee.com/link?target=https%3A%2F%2Fbaomidou.com%2F)来查看所有API的具体用法

![image-20240105160058193](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105160058721-561009491.png) 

> 若要查询所有记录，则使用如下api

```sql
// 根据 entity 条件，查询全部记录
List<T> selectList(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
```

2）指定id查用户

```java
@GetMapping("/user/{userId}")
public User getUser(@PathVariable int userId){
    return userMapper.selectById(userId);
}
```

3）区间查询

也可以使用mybatisplus中的条件构造器来进行筛选查询

![image-20240105160515688](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105160516248-1801086049.png) 

```java
@GetMapping("/user/{userId}/to/{userId2}")
public List<User> getUser(@PathVariable int userId, @PathVariable int userId2){
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.ge("id", userId).le("id",userId2);//构造条件
    return userMapper.selectList(queryWrapper);//过滤
}
```

> 可以通过链式继续加限制条件

4）插入数据

```java
@GetMapping("/user/add/{userId}/{username}/{password}")
public String addUser (
        @PathVariable int userId,
        @PathVariable String username,
        @PathVariable String password){

    User user = new User(userId, username, password);
    userMapper.insert(user);
    return "Add User Successfully";
}
```

5）删除数据

```java
@GetMapping("/user/delete/{userId}")
public String deleteUser(@PathVariable int userId){
    userMapper.deleteById(userId);
    return "Delete User Successfully";
}
```

### 集成Spring Security

使用`Spring Security`来实现登录认证

在pom.xml中添加依赖

![image-20240105161535564](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105161535986-1509425035.png) 

配置了`springboot security`之后，再没有判断登录认证的情况下，访问任意界面，均无法访问，并弹出登录界面

![image-20231220201826838](https://img2023.cnblogs.com/blog/3313059/202312/3313059-20231220201828409-1140254455.png)

> 该页面的Spring Security是自己实现的

#### login

- 默认用户名为user

- 每次启动项目，密码都会随机生成

![image-20231220201738834](https://img2023.cnblogs.com/blog/3313059/202312/3313059-20231220201741757-612256588.png)

在登录成功的访问过程中，均无需再重新登录

#### 授权验证原理

此处设计到授权验证方式：Session 类似于临时通行证，是传统的授权与验证方式，之后会使用jwt。

![image-20220722113233626](https://gitee.com/XZHongAN/king-of-bots/raw/master/%E9%A1%B9%E7%9B%AE%E7%AC%94%E8%AE%B0/%E9%85%8D%E7%BD%AEMySQL%E4%B8%8E%E6%B3%A8%E5%86%8C%E7%99%BB%E5%BD%95%E6%A8%A1%E5%9D%97.assets/image-20220722113233626-165918743918429.png)

1）登录阶段

登录成功之后，后端生成sessionID，将其同时**保存于后端数据库以及浏览器的Cookie中**

![image-20240105163709371](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105163709760-503331115.png)

2）在每次向后端SpringBoot发送请求的同时，会将`SessionID`从cookie中取出传给后端SpringBoot，然后SpringBoot向数据库查询判断当前的SessionID是否存在以及是否过期，若存在，将有关SessionID的信息（对应的用户名、过期时间）从数据库中取出，判断是否过期，如果当前的SessionID没有过期，则表示登录成功，如果发现SessionID过期或不存在，则返回给用户重新登录。

eg：登录成功会会在cookie中存入Session信息

![image-20220722115108943](https://gitee.com/XZHongAN/king-of-bots/raw/master/%E9%A1%B9%E7%9B%AE%E7%AC%94%E8%AE%B0/%E9%85%8D%E7%BD%AEMySQL%E4%B8%8E%E6%B3%A8%E5%86%8C%E7%99%BB%E5%BD%95%E6%A8%A1%E5%9D%97.assets/image-20220722115108943-165918743918431.png)

每次向后端请求时，都会取出

![image-20220722115250826](https://gitee.com/XZHongAN/king-of-bots/raw/master/%E9%A1%B9%E7%9B%AE%E7%AC%94%E8%AE%B0/%E9%85%8D%E7%BD%AEMySQL%E4%B8%8E%E6%B3%A8%E5%86%8C%E7%99%BB%E5%BD%95%E6%A8%A1%E5%9D%97.assets/image-20220722115250826-165918743918432.png)

若堆Session信息进行篡改或删除

![image-20220722115330665](https://gitee.com/XZHongAN/king-of-bots/raw/master/%E9%A1%B9%E7%9B%AE%E7%AC%94%E8%AE%B0/%E9%85%8D%E7%BD%AEMySQL%E4%B8%8E%E6%B3%A8%E5%86%8C%E7%99%BB%E5%BD%95%E6%A8%A1%E5%9D%97.assets/image-20220722115330665-165918743918434.png)

![image-20220722121136508](https://gitee.com/XZHongAN/king-of-bots/raw/master/%E9%A1%B9%E7%9B%AE%E7%AC%94%E8%AE%B0/%E9%85%8D%E7%BD%AEMySQL%E4%B8%8E%E6%B3%A8%E5%86%8C%E7%99%BB%E5%BD%95%E6%A8%A1%E5%9D%97.assets/image-20220722121136508-165918743918433.png)

再次发起请求时，由于在后端数据库中找不到对应的SessionID，返回登录界面

![image-20220722121223519](https://gitee.com/XZHongAN/king-of-bots/raw/master/%E9%A1%B9%E7%9B%AE%E7%AC%94%E8%AE%B0/%E9%85%8D%E7%BD%AEMySQL%E4%B8%8E%E6%B3%A8%E5%86%8C%E7%99%BB%E5%BD%95%E6%A8%A1%E5%9D%97.assets/image-20220722121223519-165918743918435.png) 

> SessionID相当于后端给浏览器发的一张临时身份证，后续浏览器在执行业务操作的时间，都需要随身携带这个身份证

#### logout

此外，`Spring Security`还实现了`logout`界面

> 可以理解为是其本身实现的登录 退出的controller

![image-20220722112438434](https://gitee.com/XZHongAN/king-of-bots/raw/master/%E9%A1%B9%E7%9B%AE%E7%AC%94%E8%AE%B0/%E9%85%8D%E7%BD%AEMySQL%E4%B8%8E%E6%B3%A8%E5%86%8C%E7%99%BB%E5%BD%95%E6%A8%A1%E5%9D%97.assets/image-20220722112438434-165918743918436.png) 

退出直接会继续回到最初的界面

![image-20220722112507976](https://gitee.com/XZHongAN/king-of-bots/raw/master/%E9%A1%B9%E7%9B%AE%E7%AC%94%E8%AE%B0/%E9%85%8D%E7%BD%AEMySQL%E4%B8%8E%E6%B3%A8%E5%86%8C%E7%99%BB%E5%BD%95%E6%A8%A1%E5%9D%97.assets/image-20220722112507976-165918743918437.png) 

#### 修改Spring Security

此时的登录还是通过Spring Security所提供的默认的用户名和随机生成的密码，我们需要修改springboot默认的登录用户，使其从数据库中查找用户并判断

##### UserDetailsServiceImpl

实现`service.impl.UserDetailsServiceImpl`类，继承自`UserDetailsService`接口，用来接入数据库信息

```java
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  private UserMapper userMapper;
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("username", username);
    User user = userMapper.selectOne(queryWrapper);
    if (user == null) {
      throw new RuntimeException("用户不存在");
    }
    return new UserDetailsImpl(user);
  }
}
```

此处的UserDetailsService api接受一个username，通过该username返回包含用户的用户名和密码的UserDetails接口的实现类对象

在处理User是否存在之前，需要先创建一个UserDetails的实现类：

#####  UserDetailsImpl

- 创建`service.impl.utils.UserDetailsImpl`

```java
package com.kob.backend.service.impl.utils;

import com.kob.backend.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.service.impl.utils
 * @Project：backend
 * @Date：2023/12/20 21:08
 * @Filename：UserDetailsImpl
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

  private User user; // 此处是pojo中的user，一个pojo对应一张数据表
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override // 非锁定，返回true
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}

```

如果不加显示构造函数，可以添加如下注解

![image-20240105171339447](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105171339961-707564390.png) 

现在继续回到`service.impl.UserDetailsServiceImpl`，填上最后这段代码

![image-20240105171418425](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105171419040-883173348.png) 

此时，就可以通过根据数据库中的User信息来进行登录

不过此时登录会报错：

因为数据库中未加密，需要在每个密码前面加上{noop}，提示使用明文，从而不需要使用PasswordEncoder，直接匹配

![image-20231220212952922](https://img2023.cnblogs.com/blog/3313059/202312/3313059-20231220212954622-1207865006.png)

##### 密码加密存储

从上面不难看出，若数据库中存储为明文密码，**必须声明**！

若我们需要对密码进行加密，实现`config.SecurityConfig`类，用来实现用户密码的加密存储。

eg：在注册添加用户时，直接存储加密后的密码

![image-20240105201410087](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105201411644-2023171794.png)

![image-20240105201715401](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105201716173-607277192.png)

### 集成jwt验证

#### 原理

默认情况下，使用session进行身份验证，但是对于前后端分离的情况，可能会出现跨域问题，一台浏览器的session_id，需要被多台服务器存储从而才能被验证通过的问题。

而使用JWT验证：解决了跨域难的问题。

> 传统的session验证身份的方式

![image-20240105203401060](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105203401737-974719683.png)  

1）用户进行登录时，登录成功后，后端会生成SessionID，将其同时保存在后端服务器或内存，和浏览器的Cookie中（在后端中，同样保存了SessionID和用户信息userInfo的映射关系）。

2）在每次向后端Springboot发送请求的同时，会将SessionID从Cookie中取出同样传送给后端。

3）对于需要授权访问的url，Springboot通过向数据库 or 内存查询判断当前的SessionID是否存在，如果存在，将有关SessionID的信息（包括用户名、过期时间）从数据库中取出，判断是否过期，如果发现sessionID过期或不存在，则将用户重定向至登录页面。

4）若当前sessionID没有过期，则通过SessionID和用户信息的userInfo的映射关系，将对应的User提取到上下文（在controller中可以用一些api来拿到User），成功进行授权页面的访问。

但是还在存在跨域的问题，为解决上述问题，使用jwt验证。

![image-20240105204553189](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105204553959-11349586.png) 

**主要优势：**

- 容易实现跨域

- 不需要在服务器端存储

  对于需要多台服务器提供服务器的项目，跨域实现用一个令牌来登录多台服务器

#### 配置

1）在pom.xml文件配置相关依赖

- jjwt-api
- jjwt-impl
- jjwt-jackson

2）添加相关类

- 实现`utils.JwtUtil`类，为`jwt`工具类，用来创建、解析`jwt token`

```java
package com.kob.backend.utils;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.utils
 * @Project：backend
 * @Date：2023/12/21 21:49
 * @Filename：JwtUtil
 */
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {
  // 有效期14天
  public static final long JWT_TTL = 60 * 60 * 1000L * 24 * 14;
  //秘钥
  public static final String JWT_KEY = "SDFGjhdsfalshdfHFdsjkdsfds121232131afasdfac";

  public static String getUUID() {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }

  public static String createJWT(String subject) {
    JwtBuilder builder = getJwtBuilder(subject, null, getUUID());
    return builder.compact();
  }

  private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    SecretKey secretKey = generalKey();
    long nowMillis = System.currentTimeMillis();
    Date now = new Date(nowMillis);
    if (ttlMillis == null) {
      ttlMillis = JwtUtil.JWT_TTL;
    }

    long expMillis = nowMillis + ttlMillis;
    Date expDate = new Date(expMillis);
    return Jwts.builder()
      .setId(uuid)
      .setSubject(subject)
      .setIssuer("sg")
      .setIssuedAt(now)
      .signWith(signatureAlgorithm, secretKey)
      .setExpiration(expDate);
  }

  public static SecretKey generalKey() {
    byte[] encodeKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
    return new SecretKeySpec(encodeKey, 0, encodeKey.length, "HmacSHA256");
  }

  public static Claims parseJWT(String jwt) throws Exception {
    SecretKey secretKey = generalKey();
    return Jwts.parser()
      .setSigningKey(secretKey)
      .build()
      .parseClaimsJws(jwt)
      .getBody();
  }
}
```

- 实现`config.filter.JwtAuthenticationTokenFilter`类，用来验证`jwt token`是否合法有效，如果验证成功，则将`User`信息注入上下文中

```java
package com.kob.backend.config.filter;

/**
   * @Author：Henry Wan
   * @Package：com.kob.backend.utils.filter
   * @Project：backend
   * @Date：2023/12/21 21:52
   * @Filename：JwtAuthenticationTokenFilter
   */

import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.utils.UserDetailsImpl;
import com.kob.backend.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
  @Autowired
  private UserMapper userMapper;

  @Override
  protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
    String token = request.getHeader("Authorization");

    if (!StringUtils.hasText(token) || !token.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    token = token.substring(7);

    String userid;
    try {
      Claims claims = JwtUtil.parseJWT(token);
      userid = claims.getSubject();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    User user = userMapper.selectById(Integer.parseInt(userid));

    if (user == null) {
      throw new RuntimeException("用户名未登录");
    }

    UserDetailsImpl loginUser = new UserDetailsImpl(user);
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);

    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

    filterChain.doFilter(request, response);
  }
}

```

- 配置`config.SecurityConfig`类，放行登录、注册等接口

```java
package com.kob.backend.config;

import com.kob.backend.config.filter.JwtAuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.config
 * @Project：backend
 * @Date：2023/12/20 21:36
 * @Filename：SecurityConfig
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .authorizeRequests()
      .antMatchers("/user/account/login/","/user/account/token/", "/user/account/info/", "/user/account/register/").permitAll()
      .antMatchers(HttpMethod.OPTIONS).permitAll()
      .anyRequest().authenticated();

    http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
  }
}

```

也就是对于登录和注册的URL，变为公开可以访问：（未来需要放行其他URL，也在这个地方继续添加即可）

![image-20240105205520106](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105205521079-1653168781.png) 

### 后端API实现

在具体的API实现之前，先来更新下数据库。将数据库中的id域变为自增

- 在数据库中将id列变为自增

  ![image-20240105205830293](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105205831178-1990070965.png) 

- 在`pojo.User`类中添加注解：`@TableId(type = IdType.AUTO)`

![image-20240105205902219](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105205902935-1075862947.png) 

下面是具体的API编写。在Springboot中实现API一共需要实现三个地方：

- `controller`
- `service`
- `service.impl`

![image-20240105210152583](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105210153351-1833722228.png) 

#### `sevice`

在`sevice`中写接口

![image-20240105214450371](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105214451276-150425620.png)  

#### `InfoService`

![image-20240105214609125](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105214609914-1993149616.png) 

#### `LoginService`

![image-20240105214618923](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105214619665-1628260127.png) 

#### `RegisterService`

![image-20240105214633862](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105214634596-1920344665.png) 

#### `sevice.impl`

在`service.impl`实现接口

##### `LoginServiceImpl`

![image-20240105214731959](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105214732847-122591618.png) 

其他同上

#### `controller`

![image-20240105214907550](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105214908257-1203558804.png)   

##### `LoginController`

![image-20240105214934414](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105214935280-949411278.png) 

**注：**对于登录而言，一般是post请求，如果是get请求，会将用户名和密码作为参数放在url链接中直接明文传输，而post请求看不到明文，所以一般使用`@PostMapping`注解：

将`post`请求中的参数放在Map中，所以需要使用注解`@RequestParam`

如何调试这段代码的功能呢？

由于是Post请求，所以没法从浏览器输入URL的方式进行访问，因为浏览器中对应的Get请求，不能在浏览器中调试。有两种调试方法

1）前端框架中调试

![image-20240105215643651](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105215644667-575652281.png) 

2）使用postman（更方便 推荐）

![image-20240105215833950](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105215834950-1081120783.png) 

返回的token

```
"token": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI0OWZjMDViMTY0NzE0YTgwOGQ2Zjk1NzliZmM1NDFlMCIsInN1YiI6IjEiLCJpc3MiOiJzZyIsImlhdCI6MTcwNDQ2MzA5NywiZXhwIjoxNzA1NjcyNjk3fQ.mQH-i41CArwwec5GcJEg_TTWdGHOiwvRDOqX7-zocUs"
```

使用[解析工具](https://jwt.io/)进行解析，可以看出对应的userID

![image-20231222214350639](https://img2023.cnblogs.com/blog/3313059/202312/3313059-20231222214351904-1587868898.png) 

一般而言，获取信息使用get，修改、删除、添加则使用post

调试：

![image-20240105220810170](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105220811107-228490615.png) 

使用哪个用户的token，就可以生成哪个用户的信息，至此，我们就实现了用户的登录和授权认证

至此，后端登录和注册模块的API就全部实现。

### 登录页面

#### 配置路由

新增两个页面：

- `src\views\user\account\UserAccountLoginView.vue`
- `src\views\user\account\UserAccountRegisterView.vue`

![image-20240105221327642](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105221333606-643635246.png)

并在`src\router\index.js`中为其注册路由

![image-20240105221441722](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105221442416-888578825.png) 

![image-20240105221427679](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105221428518-2135248812.png)  

#### 登录样式美化

样式改造：

借助`bootstrap`中的`Grid system`，一个用户布局的工具。

![image-20240105221653677](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105221654619-1071752964.png) 

![image-20240105221725198](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105221725995-110374718.png) 

Grid将每行分成12个模板列，允许您创建跨越任意数量列的不同元素组合。列类指示要跨越的模板列的数量。

例如，我们将登录窗口设置成跨越三个col-3，并设置居中。

效果如下：

![image-20240105221948678](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105221949922-436709990.png) 

借助`bootstrap`中的`Form controls`，负责处理表单样式。

![image-20240105222358633](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105222359706-90572897.png) 

此时还缺少一个登录按钮，用到`bootstrap`提供的`Buttons`

![image-20240105222440378](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105222441201-187009432.png) 

![image-20240105222511902](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105222512859-2044834533.png) 

![image-20240105222547253](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105222553429-994537513.png)

#### 全局信息vuex

对于每个页面而言，都需要存储当前登录的用户信息，也就是需要将用户信息设置为全局存储。

这里需要用到vue的其中一个特性vuex

创建`src\store\user.js`

将用户信息`（id，username，is_login)`以及负责授权用的`jwt-token`保存在该文件中

![image-20240105222730519](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105222731384-44055486.png)

并导入到全局modules中

![image-20240105222926115](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105222926974-1458452674.png) 

然后在`user.js`的action中编写辅助函数，现在需要发生login请求并获取token

![image-20240105223055225](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240105223059298-895030233.png) 

####  登录实现

现在在登录的主页面`views\user\account\UserAccountLoginView.vue`负责实现登录功能。

`UserAccountLoginView.vue`

`script`部分：

![image-20240106133719418](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240106133720003-1356178213.png) 

其中：

- 借助ref定义变量
- `router.push({name:'home'})`表示如果登录成功，跳转到name为home的页面

`template`部分

![image-20240106134016592](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240106134017700-320455989.png)

其中

- @submit.prevent="login"表示submit时触发login函数，并阻止默认行行为
- v-model将输入的值，与script部分使用ref定义的变量进行绑定
- {{error_message}}表示直接取出变量`error_message `的值

此时，实现了成功登录

如果正确输入用户名与密码则登录成功

![image-20240106134441406](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240106134441259-202886660.png) 

![image-20240106134451713](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240106134452158-787131372.png) 

#### 动态显示信息

在登录完成之后，我们希望在前端页面动态的显示用户信息（用户名、头像、ID等），因此需要向后端发送请求来获取当前用户的信息。

因此需要在`src\store\user.js`中增加辅助函数

![image-20240106134753180](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240106134753440-793799089.png) 

并更新`UserAccountLoginView.vue`如下：

![image-20240106135029980](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240106135030227-1198746107.png)  

也就是登录成功之后，进行获取信息，如果获取成功，就在控制台输出相应的用户信息。

但怎么将用户信息显示到导航栏呢？

需要在`components\NavBar.vue`中修改下面代码：

![image-20240106135307252](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240106135307381-713392254.png) 

若当前为登录成功，则取出用户名放在导航栏

> ​	但是此处还存在一个bug，用户登录成功后，刷新页面会变回未登录。由于此时的Jwt-token存放在浏览器的内存中，会因为刷新而被清空，需要将Jwt-token放到浏览器的local Storage中，这样的话， 即使用户关闭或刷新浏览器，都不会退至未登录状态。

#### 退出logout

用户登录之后如何退出呢？

对于整个认证机制，Jwt-token完全存在于用户本地

![image-20240106140230443](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240106140235531-926761471.png) 

Jwt-token中除了存放userID之外，还会存放一个过期时间，服务器验证的时候可以判断是否过期。

所以用户退出的逻辑很简单，直接在前端删除掉用户的jwt-token即可

同样，也是在`src\store\user.js`中写入相关辅助函数

 ![image-20240106140648128](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240106140648376-858081085.png)

然后去`src\components\NavBar.vue`中，添加一个退出的事件。

![image-20240106140842195](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240106140842411-356723907.png)

然后点击退出时，直接重定向至登录页面，注意将a标签修改为router-link

![image-20240106141311638](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240106141311792-1314001322.png) 

- 分析一下点击退出时的函数调用

![image-20231227100807723](https://img2023.cnblogs.com/blog/3313059/202312/3313059-20231227100808254-1533082656.png) 

点击退出按钮，调用`logout`函数

![image-20231227101223913](https://img2023.cnblogs.com/blog/3313059/202312/3313059-20231227101225813-599359367.png)

然后@click的logout调用 export default中的logout

![image-20231227101332815](https://img2023.cnblogs.com/blog/3313059/202312/3313059-20231227101333211-1860134413.png) 

export default中的logout 再调用user.js action中的logout

![image-20231227101500255](https://img2023.cnblogs.com/blog/3313059/202312/3313059-20231227101500889-1455608665.png) 

user.js action中的logout （同步函数会直接调这里的）最终调用mutations中的logout，完成退出

![image-20231227101609770](https://img2023.cnblogs.com/blog/3313059/202312/3313059-20231227101610261-1889651172.png)

#### 405请求方式不允许报错

405报错，方法不允许

![image-20231222201240435](https://img2023.cnblogs.com/blog/3313059/202312/3313059-20231222201241665-591772855.png)

![image-20231227102400293](https://img2023.cnblogs.com/blog/3313059/202312/3313059-20231227102401024-160131803.png)

但此时的token仅存储于用户端，刷新会使得用户退出，此外，需要将其他页面不对未登录用户开放，未登录状态下，所有使用操作都应定位至登录。

因此，还需要实现如下功能：

> 前端页面授权
>
> 注册页面
>
> 登录状态持久化 （将token存到浏览器的local store里(磁盘)）



### 前端页面授权

实现前端页面授权，也就是判断jwt-token不合法的情况，自动退出到登录界面

可以在`src\router\index.js`中实现

通过在路由里，在原来的每个路径中加一个是否需要授权的属性，为true时才可以访问

![image-20231228161701104](https://img2023.cnblogs.com/blog/3313059/202312/3313059-20231228161702323-178676731.png) 

引入`store`，来判断用户是否登录

![image-20240106142910492](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240106142910807-196796119.png) 

同时添加beforeEach函数，使用router进入某个页面之前，会执行beforeEach

![image-20231228161731725](https://img2023.cnblogs.com/blog/3313059/202312/3313059-20231228161742852-1062281861.png) 

如果目标页面需要授权，并且当前用户没有登录，则会next到登录页面

### 注册页面

实现`views\user\account\UserAccountRegisterView.vue`，实现的逻辑与登录页面一致。

![image-20240106144025998](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240106144028405-556450329.png) 

![image-20240106144048153](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240106144049345-891976575.png) 

注意：

注册阶段的`ajax`请求直接放在了`UserAccountRegisterView.vue`

而登录阶段的`ajax`请求则是：

![image-20240106144335733](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240106144336001-802364311.png)

之所以会将操作放到`user.js`中，原因是需要修改`store.state`值

![image-20240106144417816](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240106144417878-1145802379.png) 

![image-20240106144508590](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240106144508664-181244761.png)

区分一个概念：`store.state`和`store.state.user`

打印`store.state`

```
{
    "user": {
        "id": "7",
        "username": "zhou",
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIzZDVlM2RmY2FmMWY0ZDJmYmNjZmYyZjc4MTI4ZjhmMSIsInN1YiI6IjciLCJpc3MiOiJzZyIsImlhdCI6MTY1OTA3ODc2NywiZXhwIjoxNjYwMjg4MzY3fQ.3xPS9Ahls8VBNIYb_FhD7Gn_mDCTOWTK7y-8DwKmEXA",
        "is_login": true,
        "photo": "https://cdn.acwing.com/media/user/profile/photo/118942_lg_ff1a85241e.jpg"
    }
}
```

打印`store.state.user`

```
{
    "id": "7",
    "username": "zhou",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIzZDVlM2RmY2FmMWY0ZDJmYmNjZmYyZjc4MTI4ZjhmMSIsInN1YiI6IjciLCJpc3MiOiJzZyIsImlhdCI6MTY1OTA3ODc2NywiZXhwIjoxNjYwMjg4MzY3fQ.3xPS9Ahls8VBNIYb_FhD7Gn_mDCTOWTK7y-8DwKmEXA",
    "is_login": true,
    "photo": "https://cdn.acwing.com/media/user/profile/photo/118942_lg_ff1a85241e.jpg"
}
```

### 登录状态持久化

对于前面提到的bug，也就是登录成功之后刷新页面变为未登录。此时的Jwt-token存放在浏览器的内存中，具体来说是存储在`store.state.user`中的`token`变量中，会因刷新而清空，需要将Jwt-token存放在浏览器的`local Storage`中，即使用户关闭或者刷新浏览器，都不会退出登录状态。

1、在登录成功时，存储到local Storage中，在退出时，从中删除

`store\user.js`

![image-20231228193220335](https://img2023.cnblogs.com/blog/3313059/202312/3313059-20231228193221769-1942589376.png) 

![image-20231228193239495](https://img2023.cnblogs.com/blog/3313059/202312/3313059-20231228193240850-2048079051.png) 

此时，刷新仍然会使得登录状态被清空，因为刷新的时候，不会自动去判断

2、每次刷新页面时，变为未登录状态，经过`router\index.js`写入的逻辑，会重定向到登录页面

![image-20240106144838360](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240106144843618-273535214.png)

 然后我们添加相关的判断逻辑，在每次刷新页面时进入到登录页面之后，先判断`local Storage`是否有`jwt-token`，如果存在，将`jwt-token`取出验证是否有效，如果有效，则不需要重新登录，跳转到首页（home）即可

`views\user\account\UserAccountLoginView.vue`

![image-20240106145048953](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240106145054123-1556726365.png) 

此时可以初步实现效果，只不过还会存在刷新之后，由于在验证时会先经过登录页面，再进入首页，因此，登录页面会一闪而过，会有一种白影的效果。可以先让登录页面默认不展示，再判断结束之后再展示。

3、处理“白影”

新增一个变量`pulling_info`，表示当前是否正在从服务器获取信息中，如果正在拉取信息，则不展示登录页面

![image-20240106145628864](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240106145628999-1778606347.png) 

`pulling_info`为`ture`表示正在拉取信息，为`false`表示已经拉取完毕

当拉取信息结束之后，再显示对应的页面

![image-20240106145808753](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240106145808963-2059494077.png)

再没有拉取信息时，再展示登录和注册页面

![image-20240106145905827](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240106145906032-1655343727.png)

当判断完`jwt-token`是否存在和有效后，更新`pulling_info`为`false`，表示拉取结束。

注意：当验证有效时，先跳转页面，再进行的更新`pulling_info`，所以看不到“白影”。

![image-20240106150205561](https://img2023.cnblogs.com/blog/3313059/202401/3313059-20240106150205840-724582529.png)

至此，登录和注册模块完成！

### 参考资料

- 部分实验参考了[项目笔记1 Hong - Gitee.com](https://gitee.com/XZHongAN/king-of-bots/blob/master/项目笔记/配置MySQL与注册登录模块.md#修改spring-security)

- 部分笔记整理自[Acwing SpringBoot 框架课]([4.3 配置Mysql与注册登录模块（下） - AcWing](https://www.acwing.com/file_system/file/content/whole/index/content/6214835/))

