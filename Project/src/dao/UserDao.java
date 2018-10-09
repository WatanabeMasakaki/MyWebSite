package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import base.DBManager;
import beans.User;
import beans.UserDataBeans;
import ec2.Algorhythm;
import ec2.Ec2Helper;

/**
 * ユーザテーブル用のDao
 * @author takano
 *
 */
public class UserDao {

    /**
     * ログインIDとパスワードに紐づくユーザ情報を返す
     * @param loginId
     * @param password
     * @return
     */
    public User findByLoginInfo(String loginid, String password) {
        Connection conn = null;

        try {
            // データベースへ接続
            conn = DBManager.getConnection();

            // SELECT文を準備
            String sql = "SELECT * FROM ec2_user WHERE login_id = ? and password = ?";

            // SELECT文を実行し、結果表を取得
           PreparedStatement pStmt = conn.prepareStatement(sql);
           pStmt.setString(1, loginid);
           pStmt.setString(2, Ec2Helper.getSha256(password));//前はAlgorhythm.getMD5で暗号化
           ResultSet rs = pStmt.executeQuery();


           // 主キーに紐づくレコードは1件のみなので、rs.next()は1回だけ行う
          if (!rs.next()) {
              return null;
          }


          String loginidData = rs.getString("login_id");
          String nameData = rs.getString("name");
          return new User(loginidData, nameData);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            // データベース切断
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }
    /**
     * 全てのユーザ情報を取得する
     * @return
     */
    public List<User> findAll() {
        Connection conn = null;
        List<User> userList = new ArrayList<User>();

        try {
            // データベースへ接続
            conn = DBManager.getConnection();

            // SELECT文を準備
            String sql = "SELECT * FROM ec2_user where login_id not in ('admin')";

             // SELECTを実行し、結果表を取得
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // 結果表に格納されたレコードの内容を
            // Userインスタンスに設定し、ArrayListインスタンスに追加
            while (rs.next()) {
                int id = rs.getInt("id");
                String loginId = rs.getString("login_id");
                String name = rs.getString("name");
                Date birthDate = rs.getDate("birth_date");
                String password = rs.getString("password");
                String createDate = rs.getString("create_date");
                String updateDate = rs.getString("update_date");
                User user = new User(id, loginId, name, birthDate, password, createDate, updateDate);

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            // データベース切断
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return userList;
    }
    /**
     * ユーザー情報の新規登録（ログインID、パスワード、ユーザー名、生年月日)
     *
     */
    public int findByUserSignupInfo(String loginid,String password,String username,String birthdate) {
    	Connection conn = null;

    	try {
    	// データベースへ接続
        conn = DBManager.getConnection();

        // INSERT文を準備
        String sql = "INSERT INTO ec2_user (login_id, password, name, birth_date, create_date, update_date) "
        		+ "VALUES(?,?,?,?,NOW(),NOW()) ";

        // INSERT文を実行し、結果表を取得
        PreparedStatement pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, loginid);
        pStmt.setString(2, Algorhythm.getMD5(password));
        pStmt.setString(3, username);
        pStmt.setString(4, birthdate);
        int result = pStmt.executeUpdate();
        return result;
    	} catch(SQLException e) { //ここ(SQLException)で、入力したデータにエラー(重複)があった場合はそのエラーを拾い戻り値(result)は0になる。
            e.printStackTrace();
            return 0;
        } finally {
            // データベース切断
            if (conn != null) {
                try {
                    conn.close();
              } catch (SQLException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        }
    }
    /**
     * idに紐づくユーザ情報を返す
     * @param id
     */
    public User findByUserDetailInfo(int id2) {
        Connection conn = null;

        try {
            // データベースへ接続
            conn = DBManager.getConnection();

            // SELECT文を準備
            String sql = "SELECT * FROM ec2_user WHERE id = ?";

            // SELECTを実行し、結果表を取得
           PreparedStatement pStmt = conn.prepareStatement(sql);
           pStmt.setInt(1, id2); //id2は"1"が入っているので、SELECT文は"SELECT * FROM user WHERE id = 1"となり『idが1のレコードの情報を取得する』になる。
           ResultSet rs = pStmt.executeQuery(); //rsにレコードの情報を代入する。

           // 主キーに紐づくレコードは1件のみなので、rs.next()は1回だけ行う
          if (!rs.next()) {
              return null;
          }
          int id3 = rs.getInt("id"); //DBのuserテーブルのid=1のレコードのidカラムの値を(1)id3に代入する。
          String loginid = rs.getString("login_id");
          String name = rs.getString("name");
          Date birthDate = rs.getDate("birth_date");
          String password = rs.getString("password");
          String createDate = rs.getString("create_date");
          String updateDate = rs.getString("update_date");
          return new User(id3,loginid, name, birthDate, password,createDate, updateDate);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            // データベース切断
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }
    /**
     * ユーザー情報の更新(パスワード,ユーザー名,生年月日)
     *
     */
    public void findByUserUpdateInfo(String password,String username,String birthdate,String id) {
        Connection conn = null;

        try {
            // データベースへ接続
            conn = DBManager.getConnection();

            // UPDATE文を準備
            String sql = "UPDATE ec2_user SET password = ?,name = ?,birth_date = ? WHERE id = ?";

            // UPDATE文を実行し、結果表を取得
           PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, Algorhythm.getMD5(password));
            pStmt.setString(2, username);
            pStmt.setString(3, birthdate);
            pStmt.setString(4, id);
            pStmt.executeUpdate(); //戻り値なしでDB側に設定するだけでいい。

          } catch (SQLException e) {
              e.printStackTrace();
          } finally {
              // データベース切断
              if (conn != null) {
                  try {
                      conn.close();
                  } catch (SQLException e) {
                      e.printStackTrace();
                  }
              }
          }
      }
    /**
     * ユーザー情報の削除（id)
     * @return
     *
     */
    public void findByUserDeleteInfo(String id2) {
        Connection conn = null;

        try {
            // データベースへ接続
            conn = DBManager.getConnection();

            // DELETE文を準備
            String sql = "DELETE FROM ec2_user WHERE id = ?;";

            // DELETE文を実行し、結果表を取得
           PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, id2);
            pStmt.executeUpdate();

          } catch (SQLException e) {
              e.printStackTrace();
          } finally {
              // データベース切断
              if (conn != null) {
                  try {
                      conn.close();
                  } catch (SQLException e) {
                      e.printStackTrace();
                  }
              }
          }
      }
	/**
	 * ユーザーIDを取得
	 *
	 * @param loginId
	 *            ログインID
	 * @param password
	 *            パスワード
	 * @return int ログインIDとパスワードが正しい場合対象のユーザーID 正しくない||登録されていない場合0
	 * @throws SQLException
	 *             呼び出し元にスロー
	 */
	public static int getUserId(String loginId, String password) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DBManager.getConnection();

			st = con.prepareStatement("SELECT * FROM ec2_user WHERE login_id = ?");
			st.setString(1, loginId);

			ResultSet rs = st.executeQuery();

			int userId = 0;
			while (rs.next()) {
				if (Ec2Helper.getSha256(password).equals(rs.getString("password"))) {
					userId = rs.getInt("id");
					System.out.println("login succeeded");
					break;
				}
			}

			//String ah = Ec2Helper.getSha256(password);
            //System.out.println(ah);
            //5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8

			System.out.println("searching userId by loginId has been completed");
			return userId;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}
	/**
	 * ユーザーIDからユーザー情報を取得する
	 *
	 * @param useId
	 *            ユーザーID
	 * @return udbList 引数から受け取った値に対応するデータを格納する
	 * @throws SQLException
	 *             呼び出し元にcatchさせるためスロー
	 */
	public static UserDataBeans getUserDataBeansByUserId(int userId) throws SQLException {
		UserDataBeans udb = new UserDataBeans();
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DBManager.getConnection();
			st = con.prepareStatement("SELECT id,name, login_id FROM ec2_user WHERE id =" + userId);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				udb.setId(rs.getInt("id"));
				udb.setName(rs.getString("name"));
				udb.setLoginId(rs.getString("login_id"));
				//udb.setAddress(rs.getString("address"));
			}

			st.close();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}

		System.out.println("searching UserDataBeans by userId has been completed");
		return udb;
	}
    /**
     * ユーザー一覧の検索機能（ログインID、ユーザー名、生年月日)
     *
     */
    public List<User> findbyUserListInfo(String loginid,String username,String datestart,String dateend) {

        Connection conn = null;
        List<User> userList = new ArrayList<User>();

        try {
            // データベースへ接続
            conn = DBManager.getConnection();

            // SELECT文を準備
            String sql = "SELECT * FROM ec2_user where login_id not in ('admin')"; //admin以外のユーザーを出力

            if (!loginid.equals("")) {                      //ログインIDフォームが未入力以外だった場合
            	sql += " and login_id = '" + loginid + "'"; //上の↑SQL文に付け足す文
            }

            if (!username.equals("")) {
            	sql += " and name LIKE  '%"+ username +"%'";
            }

            if (!datestart.equals("") ) {
            	sql += " and birth_date >= '"+ datestart +"'";
            }

            if (!dateend.equals("") ) {
            	sql += " and birth_date <= '"+ dateend +"'";
            }

            System.out.println(sql);

             // SELECTを実行し、結果表を取得
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // 結果表に格納されたレコードの内容を
            // Userインスタンスに設定し、ArrayListインスタンスに追加
            while (rs.next()) {
                int id = rs.getInt("id");
                String loginId = rs.getString("login_id");
                String name = rs.getString("name");
                Date birthDate = rs.getDate("birth_date");
                String password = rs.getString("password");
                String createDate = rs.getString("create_date");
                String updateDate = rs.getString("update_date");
                User user = new User(id, loginId, name, birthDate, password, createDate, updateDate);

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            // データベース切断
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return userList;
   }
}
