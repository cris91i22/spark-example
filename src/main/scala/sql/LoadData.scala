package sql

import java.sql.{Connection, DriverManager}

trait LoadData {

  this: SparkConfig =>

  def run(qtyOfRows: Int): Unit = {
    var connection: Connection = null

    try {
      // make the connection
      Class.forName(driver)
      connection = DriverManager.getConnection(jdbcUrl, user, password)

      // create the statement, and run the select query
      val statement = connection.createStatement()

      if (!connection.getMetaData.getTables(null, null, dbTable, null).next()){
        statement.addBatch("CREATE TABLE spark(id bigserial primary key, name varchar(20) NOT NULL)")
      }

      (0 to qtyOfRows)foreach{ i => statement.addBatch(s"INSERT INTO spark (name) VALUES ('coco $i')") }
    } catch {
      case e: Exception => e.printStackTrace()
    }

    connection.close()
  }

}
