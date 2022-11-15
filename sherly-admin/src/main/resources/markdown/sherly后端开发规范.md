<h1 align="center">sherly后端开发规范</h1>
<p align="center">
	<a href='https://gitee.com/guzi499/universal-practice-repository/stargazers'>
        <img src='https://gitee.com/guzi499/universal-practice-repository/badge/star.svg?theme=dark' alt='star'/>
    </a>
</p>


### 编程规约
#### 【1】命名风格
1. 类名使用UpperCamelCase风格，但以下情形例外：DTO / VO / EO。如UserDTO, UserVO；
2. 常量命名全部大写，单词间用下划线隔开，力求语义表达完整清楚，不要嫌名字长。
3. 接口类中的方法和属性不要加任何修饰符号（public 也不要加），保持代码的简洁性，并加上有效的Javadoc注释。尽量不要在接口里定义变量，如果一定要定义变量，确定与接口方法相关，并且是整个应用的基础常量。
4. 请求uri中不可使用驼峰式命名，使用下划线"_"隔离单词。
5. 各层命名规约：
    - 对单个对象的方法和uri用get做前缀，如`getOne()`， `"get_one"`。
    - 获取多个对象的方法和uri用list做前缀，如`listPage()`，`"list_page"`，`listTree()`，`"list_tree"`。
    - 获取统计值的方法和uri用count做前缀
    - 插入的方法和uri用save做前缀，如`saveOne()`， `"save_one"`，`saveAll()`， `"save_all"`。
    - 删除的方法和uri用remove做前缀，如`removeOne()`， `"remove_one"`。
    - 修改的方法和uri用update做前缀，如`updateOne()`， `"update_one"`。
    - ===========================
    - 查询的时候使用QueryDTO，QueryVO做后缀，如果是分页查询，DTO必须继承PageQuery，而不可直接使用PageQuery
    - 新增的时候使用InsertDTO，InsertVO做后缀
    - 更新的时候使用UpdateDTO，UpdateVO做后缀
    - ===========================
6. 所有注入一律使用 @Resource，不要使用 @Autowired。
#### 【2】OOP规约      
1. 关于基本数据类型与包装数据类型的使用标准如下： 
   - 所有的POJO类属性必须使用包装数据类型。 
   - RPC方法的返回值和参数必须使用包装数据类型。 
   - 所有的局部变量使用基本数据类型。
2. 定义DO/VO等POJO类时，不要设定任何属性默认值。
3. 构造方法里面禁止加入任何业务逻辑，如果有初始化逻辑，请放在init方法中。
4. 循环体内，字符串的连接方式，使用StringBuilder的append方法进行扩展。
#### 【3】控制语句
1. 在if/else/for/while/do语句中必须使用大括号。
2.表达异常的分支时，少用if-else方式。
3.避免采用取反逻辑运算符。
#### 【4】注释规约
1. 类、类属性、类方法的注释必须使用Javadoc规范，使用`/**内容*/`格式，不得使用// xxx方式。类属性必须使用单行文档注释。
2. 所有的抽象方法（包括接口中的方法）必须要用Javadoc注释、除了返回值、参数、异常说明外，还必须指出该方法做什么事情，实现什么功能。
3. 所有的类都必须添加创建者和创建日期。
   - 方法内部单行注释，在被注释语句上方另起一行，使用//注释。方法内部多行注释使用/* */注释，注意与代码对齐。
4. 代码修改的同时，注释也要进行相应的修改，尤其是参数、返回值、异常、核心逻辑等的修改。
5. 谨慎注释掉代码。在上方详细说明，而不是简单地注释掉。如果无用，则删除。
6. 特殊注释标记，请注明标记人与标记时间。注意及时处理这些标记，通过标记扫描，经常清理此类标记。线上故障有时候就是来源于这些标记处的代码。 
   - 待办事宜（TODO）:（标记人，标记时间，[预计处理时间]） 表示需要实现，但目前还未实现的功能。这实际上是一个Javadoc的标签，目前的Javadoc还没 有实现，但已经被广泛使用。只能应用于类，接口和方法（因为它是一个Javadoc标签）。 
   - 错误，不能工作（FIXME）:（标记人，标记时间，[预计处理时间]） 在注释中用FIXME标记某代码是错误的，而且不能工作，需要及时纠正的情况。
7. 所有人的类注释都应该统一
   - ```java
        /**
         * 
         * @author 谷子毅
         * @date ${DATE}
         */
     ```
#### 【5】前后端规约
1. 查询必须使用GET，新增使用POST，修改使用PUT，删除使用DELETE。
    - POST请求和PUT请求必须使用@RequestBody 定义DTO，application/json方式传输数据。
    - 接口命名统一为XX新增、XX删除、XX修改、XX分页、XX详情等。
2. 禁止使用pathVariable，即{id}方式，如果没有校验，可能会导致路径跳转错误。
3. 前后端的时间格式统一为"yyyy-MM-dd HH:mm:ss"，统一为GMT。
#### 【6】Git提交规范
1. git提交时需要填写message，内容格式如下
   ```text
      【需求编号】类型:描述
   ```
   多个提交则换行，例如
   ```text
      【1000024】feat:实现用户的导出功能
      【1000007】fix:解决查看用户页面空指针问题
      【0000000】doc:更新后端开发规范文档
   ```
2. 下面列举所有的git提交类型
   ```text
      feat -> 新功能
      fix -> bug修复
      doc -> 文档更新
      style -> 代码格式变更
      refactor -> 重构
      perf -> 性能优化
      test -> 新增测试
      revert -> 回滚
   ```
#### 【7】knife4j文档
1. 所有VO/DTO都需要加**行形式**文档注释和@ApiModelProperty注解
2. 所有Controller类上需要加@Api(tags = "xxx")注解
3. 所有Controller方法需要加@ApiOperation("xxx")注解
4. 如果Result中data有数据，Controller方法中返回值必须写完整的类型。如 Result<List<UserVO>>。
#### 【8】mybatis-plus
1. 因为加了mybatis-plus逻辑删除。所有逻辑删除不要使用update更新，而是直接删除。
2. 所有自增主键一律加注解 @TableId(type = IdType.AUTO)。
#### 【9】lombok
1. @Data：注解在类上，相当于同时使用了@ToString、@EqualsAndHashCode、@Getter、@Setter和@RequiredArgsConstrutor这些注解，对于POJO类十分有用。除特殊情况，不要再添加其他lombok注解。
#### 【10】validation
1. 增删改时必填字段,只加在DTO字段上：如果为字符串类型使用@NotBlank注解，如果为其他类型使用@NotNull注解
#### 【11】其他
1. 不要在视图模板中加入任何复杂的逻辑，即前端只负责展示，不参与任何业务处理！
### 异常日志
#### 【1】错误码
1. 使用ResultXXXEnum枚举新增异常类型，code共9位为错误码，message为错误信息，code结构为**服务3位**+**模块3位**+**编号3位**。
#### 【2】异常处理
1. 系统定义了全局异常处理器，分别处理业务异常，空指针异常和其他异常四种。如果代码会出现可能的异常，请使用throw抛出异常交给全局异常处理器处理，而不是自己使用try-catch。
#### 【3】日志规约
### 数据库设计
#### 【1】表设计
1. 一般来说，主键策略选择自增id，使用unsigned bigint
2. 一般来说，时间类型选择datetime
3. 一般来说，小的枚举类型使用tinyint
### 工程结构
#### 【1】应用分层
1. 根据业务架构，将系统分为四层。controller、service、manager、dao、mapper。
    - 其中controller层只做接收DTO，调用service层获取VO,并包装返回Result。
    - 其中service层只做高级业务逻辑，service层不允许有数据库相关内容，与操作数据库相关代码一律编写在Dao层。
    - 其中manager层只做通用业务逻辑，如对象存储，日志等通用业务代码。是为了防止service层之间互相调用导致的循环依赖，manager层不允许有数据库相关内容，与操作数据库相关代码一律编写在Dao层。
    - 其中dao层只做数据库CRUD，继承mybatis-plus的ServiceImpl，可直接调用简单增删改查。否则一律使用wrapper包装生成sql。
    - 其中mapper层只负责mybatis-plus的mapper拓展和简单sql编写，无其他作用。

### 版本记录
- `v1.0 & 2022-03-31 : 初稿编写完成。`
- `v1.1 & 2022-04-11 : 更新完善`
- `v1.2 & 2022-05-13 : 更新校验注解的使用`
- `v1.3 & 2022-06-16 : 更新git提交规范`
- `v1.4 & 2022-07-19 : 更新mysql表设计规范`
