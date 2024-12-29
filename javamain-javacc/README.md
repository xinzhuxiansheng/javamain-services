## 基本语法
```
options {
    全局选项
}

PARSER_BEGIN(解析器类名)

package 包名

import 库名

public class 解析器类名 {
  // 任意的 Java 代码
}

PARSER_END(解析器类名)
 
TOKENS定义（描述词法规则）
 
Productions（生成式）定义（描述文法规则）
```




## 操作命令
```
javacc xxx.jj
javac -Xlint:unchecked *.java
java xxx
```

## example目录
adder_exampleX 实现 加法的案例
calculator_exampleX 实现 计算器的案例