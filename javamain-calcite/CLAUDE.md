- # Steps to implementation
  * Implement `SqlBase64Decode` class by extending `SqlFunction`. It is only necessary to
   call a proper constructor.
  * Add an instance of the function to `ExtendedSqlOperatorTable`.

你需要在 CsvQueryTest 实现 udf 函数 ， 这个是它的调用示例：SELECT base64decode(store_and_fwd_flag, 2) from