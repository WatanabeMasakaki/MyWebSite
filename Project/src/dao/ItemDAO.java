package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import base.DBManager;
import beans.ItemDataBeans;

/**
 *
 * @author d-yamaguchi
 *
 */
public class ItemDAO {



	/**
	 * ランダムで引数指定分のItemDataBeansを取得
	 * @param limit 取得したいかず
	 * @return <ItemDataBeans>
	 * @throws SQLException
	 */
	public static ArrayList<ItemDataBeans> getRandItem(int limit) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DBManager.getConnection();

			st = con.prepareStatement("SELECT * FROM ec2_item ORDER BY RAND() LIMIT ? ");
			st.setInt(1, limit);

			ResultSet rs = st.executeQuery();

			ArrayList<ItemDataBeans> itemList = new ArrayList<ItemDataBeans>();

			while (rs.next()) {
				ItemDataBeans item = new ItemDataBeans();
				item.setId(rs.getInt("id"));
				item.setName(rs.getString("name"));
				item.setDetail(rs.getString("detail"));
				item.setPrice(rs.getInt("price"));
				item.setFileName(rs.getString("file_name"));
				itemList.add(item);
			}
			System.out.println("getAllItem completed");
			return itemList;
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
	 * 商品IDによる商品検索
	 * @param itemId
	 * @return ItemDataBeans
	 * @throws SQLException
	 */
	public static ItemDataBeans getItemByItemID(int itemId) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DBManager.getConnection();

			st = con.prepareStatement("SELECT * FROM ec2_item WHERE id = ?");
			st.setInt(1, itemId);

			ResultSet rs = st.executeQuery();

			ItemDataBeans item = new ItemDataBeans();
			if (rs.next()) {
				item.setId(rs.getInt("id"));
				item.setName(rs.getString("name"));
				item.setDetail(rs.getString("detail"));//"name"を"detail"に修正
				item.setPrice(rs.getInt("price"));
				item.setFileName(rs.getString("file_name"));
			}

			System.out.println("searching item by itemID has been completed");

			return item;
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
	 * 商品総数を取得
	 *
	 * @param itemSerchWord
	 * @return
	 * @throws SQLException
	 */
	public static double getItemCount(String itemSerchWord) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DBManager.getConnection();
			st = con.prepareStatement("select count(*) as cnt from ec2_item where name like ?");
			st.setString(1, "%" + itemSerchWord + "%");
			ResultSet rs = st.executeQuery();
			double coung = 0.0;
			while (rs.next()) {
				coung = Double.parseDouble(rs.getString("cnt"));
			}
			return coung;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}
	/**
	 * 商品名に紐づく商品検索(部分一致検索)
	 * @param searchWord
	 */
	public static ArrayList<ItemDataBeans> getItemsByItemSerchWord(String itemSerchWord) {
		Connection conn = null;
		List<ItemDataBeans> itemList = new ArrayList<ItemDataBeans>();

		try {
            // データベースへ接続
            conn = DBManager.getConnection();

            // SELECT文を準備
            String sql = "SELECT * FROM ec2_item WHERE name LIKE ?";


            // SELECT文を実行し、結果表を取得
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, "%"+ itemSerchWord +"%");
            ResultSet rs = pStmt.executeQuery();

            // 結果表に格納されたレコードの内容を
            // ItemDataBeansインスタンスに設定し、ArrayListインスタンスに追加
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String detail = rs.getString("detail");
                int price = rs.getInt("price");
                String filename = rs.getString("file_name");
                ItemDataBeans item = new ItemDataBeans(id,name,detail,price,filename);
                itemList.add(item);
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
        return (ArrayList<ItemDataBeans>) itemList;
    }
	/**
	 * 商品名に紐づく商品検索(部分一致検索)
	 * @param searchWord
	 * @param pageNum
	 * @param pageMaxItemCount
	 * @return
	 * @throws SQLException
	 */
	public static ArrayList<ItemDataBeans> getItemsByItemName(String searchWord, int pageNum, int pageMaxItemCount) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		try {
			int startiItemNum = (pageNum - 1) * pageMaxItemCount;
			con = DBManager.getConnection();

			if (searchWord.length() == 0) {                  //検索ワードの長さが０の場合
				// 全検索
				st = con.prepareStatement("SELECT * FROM ec2_item ORDER BY id ASC LIMIT ?,? ");
				st.setInt(1, startiItemNum);
				st.setInt(2, pageMaxItemCount);
			} else {
				// 商品名検索
				st = con.prepareStatement("SELECT * FROM ec2_item WHERE name like ?  ORDER BY id ASC LIMIT ?,? ");
				st.setString(1,"%" + searchWord + "%");//st.setString(1,searchWord);をst.setString(1,"%" + searchWord + "%");に修正
				st.setInt(2, startiItemNum);
				st.setInt(3, pageMaxItemCount);
			}

			ResultSet rs = st.executeQuery();
			ArrayList<ItemDataBeans> itemList = new ArrayList<ItemDataBeans>();

			while (rs.next()) {
				ItemDataBeans item = new ItemDataBeans();
				item.setId(rs.getInt("id"));
				item.setName(rs.getString("name"));
				item.setDetail(rs.getString("detail"));
				item.setPrice(rs.getInt("price"));
				item.setFileName(rs.getString("file_name"));
				itemList.add(item);
			}
			System.out.println("get Items by itemName has been completed");
			return itemList;
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
     * 全ての商品情報を取得する(マスタデータ)
     * @return
     */
    public List<ItemDataBeans> getAllItemData() {
        Connection conn = null;
        List<ItemDataBeans> itemList = new ArrayList<ItemDataBeans>();

        try {
            // データベースへ接続
            conn = DBManager.getConnection();

            // SELECT文を準備
            String sql = "SELECT * FROM ec2_item";

             // SELECTを実行し、結果表を取得
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // 結果表に格納されたレコードの内容を
            // ItemDataBeansインスタンスに設定し、ArrayListインスタンスに追加
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String detail = rs.getString("detail");
                int price = rs.getInt("price");
                String filename = rs.getString("file_name");
                ItemDataBeans item = new ItemDataBeans(id,name,detail,price,filename);
                itemList.add(item);
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
        return itemList;
    }
    /**
     * 商品情報の新規登録（商品名、商品詳細、商品価格、画像ファイル)
     *
     */
    public int findByItemSignupInfo(String itemname,String itemdetail,String itemprice,String name) {
    	Connection conn = null;

    	try {
    	// データベースへ接続
        conn = DBManager.getConnection();

        // INSERT文を準備
        String sql = "INSERT INTO ec2_item (name, detail, price, file_name) "
        		+ "VALUES(?,?,?,?)";

        // INSERT文を実行し、結果表を取得
        PreparedStatement pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, itemname);
        pStmt.setString(2, itemdetail);
        pStmt.setString(3, itemprice);
        pStmt.setString(4, name);
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
     * 商品情報の更新(商品名,商品詳細,商品価格)
     *
     */
    public void findByItemUpdateInfo(String itemname,String itemdetail,String itemprice,String itemid) {
        Connection conn = null;

        try {
            // データベースへ接続
            conn = DBManager.getConnection();

            // UPDATE文を準備
            String sql = "UPDATE ec2_item SET name = ?,detail = ?,price = ? WHERE id = ?";

            // UPDATE文を実行し、結果表を取得
           PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, itemname);
            pStmt.setString(2, itemdetail);
            pStmt.setString(3, itemprice);
            pStmt.setString(4, itemid);
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
     * idに紐づく商品情報を返す
     * @param id
     */
    public ItemDataBeans findByItemDetailInfo(int id2) {
        Connection conn = null;

        try {
            // データベースへ接続
            conn = DBManager.getConnection();

            // SELECT文を準備
            String sql = "SELECT * FROM ec2_item WHERE id = ?";

            // SELECTを実行し、結果表を取得
           PreparedStatement pStmt = conn.prepareStatement(sql);
           pStmt.setInt(1, id2); //id2は"1"が入っているので、SELECT文は"SELECT * FROM user WHERE id = 1"となり『idが1のレコードの情報を取得する』になる。
           ResultSet rs = pStmt.executeQuery(); //rsにレコードの情報を代入する。

           // 主キーに紐づくレコードは1件のみなので、rs.next()は1回だけ行う
          if (!rs.next()) {
              return null;
          }
          int id3 = rs.getInt("id");
          String name = rs.getString("name");
          String detail = rs.getString("detail");
          int price = rs.getInt("price");
          String filename = rs.getString("file_name");
          return new ItemDataBeans(id3,name, detail, price,filename);

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
     * 商品情報の削除
     * @return
     *
     */
    public void findByItemDeleteInfo(String id2) {
        Connection conn = null;

        try {
            // データベースへ接続
            conn = DBManager.getConnection();

            // DELETE文を準備
            String sql = "DELETE FROM ec2_item WHERE id = ?;";

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
}
