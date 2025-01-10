package kadai_007;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Posts_Chapter07 {
	public static void main(String[] args) {
		
		Connection con = null;
		PreparedStatement statement = null;
		Statement statement2 = null;
		
		// 追加内容リスト
		String[][]posts = {
				{"1003","2023-02-08","昨日は徹夜でした・・・","13"},
				{"1002","2023-02-08","お疲れ様です！","12"},
				{"1003","2023-02-09","今日も頑張ります！","18"},
				{"1001","2023-02-09","無理は禁物ですよ！","17"},
				{"1002","2023-02-10","明日から連休ですね！","20"}
		};
				
	
		try {
			//データベースに接続
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/challenge_java",
				"root",
				"JavaOKds"
				);
			
			 System.out.println("データベース接続成功:" + con);
			 
			 //SQLクエリを準備
			 
			 //データを追加するSQLクエリ
			 String sql = "INSERT INTO posts(user_id,posted_at,post_content,likes) VALUES(?,?,?,?)";
			 statement = con.prepareStatement(sql);
			 
			 
			 //リストの１行目から順番に読み込む
			 int rowCnt = 0;
			 for(int i = 0; i<posts.length; i++) {
				 //SQLクエリの「？」部分をリストのデータに置き換え
				 statement.setString(1,posts[i][0]);//user_id
				 statement.setString(2,posts[i][1]);//日付
				 statement.setString(3,posts[i][2]);//内容
				 statement.setString(4,posts[i][3]);//いいね
				 
				 //SQLクエリを実行（DBMSに送信）
				 rowCnt = statement.executeUpdate();
			 }
				 System.out.println("レコード追加を実行します");
				 
				 System.out.println(posts.length + "件のレコードが追加されました");
			
			 
			 //データを検索するSQLクエリ
			 String sql2 = "SELECT posted_at,post_content,likes FROM posts WHERE user_id=1002";
			 statement2 = con.createStatement();
			 
			 System.out.println("ユーザーIDが1002のレコードを検索しました");
			 
			     //SQLクエリを実行
			     ResultSet result = statement2.executeQuery(sql2);
			     
			     //SQLクエリの実行結果を抽出・表示
			     while(result.next()) {
			    	 Date postedAt = result.getDate("posted_at");
			    	 String content = result.getString("post_content");
			    	 int likes = result.getInt("likes");
			    	 
			    	 System.out.println(result.getRow() + "件目:投稿日時=" + postedAt + "/投稿内容=" + content + "/いいね数=" +likes);
			     }
			     
		}catch(SQLException e) {
			System.out.println("エラー発生:" + e.getMessage());
		}finally {
			//使用したオブジェクトを解放
			if (statement != null) {
				try {statement.close();} catch(SQLException ignore) {}
			}
			if (statement2 != null) {
				try {statement2.close();} catch(SQLException ignore) {}
			}
			if (con != null) {
				try { con.close();}catch(SQLException ignore) {}
			}
		}

	}

}
