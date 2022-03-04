import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class JDBCtest {
    public static void main(String[] args) throws Exception{

        //构造器可带参数：指明要使用的配置文件的名称
        /*
        DataSource ds=new ComboPooledDataSource();

        Connection con=ds.getConnection();
        System.out.println(con);*/

       /*
        Properties pro=new Properties(20);

        pro.load(new FileReader("C:/Users/nameless/IdeaProjects/untitled8/src/druid-config.properties"));
         */

        JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
        //模板对象需要一个DataSource对象来创建

         String sql ="select * from employee where employee_id = ? order by employee_id asc";
         //List list=template.queryForList(sql,new Scanner(System.in).nextInt());
         //模板对象有多种query方法，可以直接将字符串作为（prepared）Statemnt执行，并返回期望的结果
         //此处自动将返回的表封装为对象，并存入List中
           List<Map<String,Object>> mapList = template.queryForList(sql,new Scanner(System.in).nextInt());
           System.out.println(mapList);
           String sql2="select * from employee";
          template.query(sql2, new RowMapper<Object>() {

              @Override
              public Object mapRow(ResultSet resultSet, int i) throws SQLException
              {return null;
              }

          });
         //JDBCUtils.close(rs,ps,col); 模板对象可以自动关闭连接 ，无须手动操作
    }
}
class JDBCUtils{
    private static DataSource ds;

    static {
        Properties pro=new Properties();
        try {
            pro.load(JDBCUtils.class.getClassLoader().getResourceAsStream("druid-config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ds=DruidDataSourceFactory.createDataSource(pro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static  void close(Statement s,Connection c)  {
        if(s!=null){
            try {
                s.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
            if (c!=null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    }
    public static  void close(ResultSet r,Statement s, Connection c)  {
         close(s,c);
        if(r!=null)
        {
            try {
                r.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //获取连接池方法
  public static DataSource getDataSource(){
        return ds;
    }
}
class employees{
    Integer e_id;
}