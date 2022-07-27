/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package B7_JDBC.repository;

import B7_JDBC.model.GiangVien;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author hangnt
 */
public class GiangVienRepository {

    public List<GiangVien> getAll() {
        // B1: Viet cau truy van
        String query = "SELECT ma_gv, ten_gv, tuoi, bac, loai, gioi_tinh "
                + "FROM B7_TrenLop.dbo.giang_vien";

        // B2: Mo cong ket noi
        // Connection: La 1 inteface cung cap cac method de giao tiep vs Database
        try ( Connection con = SQLServerConnection.getConnection();  PreparedStatement ps = con.prepareStatement(query);) {
            // Statement: La 1 interface dung de gui cac cau query sang DB
            ResultSet rs = ps.executeQuery();
            // ResultSet: dai dien tap hop tat ca cac record( ban ghi) la ket qua tra ve cua cau sql
            // B4: Tao ra 1 list
            List<GiangVien> listGiangVien = new ArrayList<>();
            // B5: Convert 
            while (rs.next()) {
                GiangVien gv = new GiangVien(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getBoolean(6));
                listGiangVien.add(gv);
            }
            return listGiangVien;
        } catch (SQLException e) {
            // SQLException: Dung de bat tat ca loi lien quan toi viec giao tiep DB
            e.printStackTrace(System.out);
        }
        return null;
    }

    public GiangVien getOne(String maGV) {
        String query = "SELECT * FROM giang_vien gv  WHERE ma_gv = ? ";
        try ( Connection con = SQLServerConnection.getConnection();  PreparedStatement ps = con.prepareStatement(query);) {
            // Statement: La 1 interface dung de gui cac cau query sang DB
            ps.setObject(1, maGV);
            ResultSet rs = ps.executeQuery();
            // ResultSet: dai dien tap hop tat ca cac record( ban ghi) la ket qua tra ve cua cau sql
            // B5: Convert 
            if (rs.next()) {
                GiangVien gv = new GiangVien(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getBoolean(6));
                return gv;
            }
        } catch (SQLException e) {
            // SQLException: Dung de bat tat ca loi lien quan toi viec giao tiep DB
            e.printStackTrace(System.out);
        }
        return null;
    }

    public boolean add(GiangVien gv) {
        String query = "INSERT INTO B7_TrenLop.dbo.giang_vien "
                + "(ma_gv, ten_gv, tuoi, bac, loai, gioi_tinh) "
                + "VALUES(?,?,?,?,?,?)";
        int check = 0;
        try ( Connection con = SQLServerConnection.getConnection();  PreparedStatement ps = con.prepareStatement(query);) {
            // Statement: La 1 interface dung de gui cac cau query sang DB
            ps.setObject(1, gv.getMaGV());
            ps.setObject(2, gv.getTenGV());
            ps.setObject(3, gv.getTuoi());
            ps.setObject(4, gv.getBac());
            ps.setObject(5, gv.getLoai());
            ps.setObject(6, gv.isGioiTinh());
            check = ps.executeUpdate();
        } catch (SQLException e) {
            // SQLException: Dung de bat tat ca loi lien quan toi viec giao tiep DB
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public boolean delete(String maGV) {
        String query = "DELETE FROM giang_vien \n"
                + "WHERE ma_gv = ?";
        int check = 0;
        try ( Connection con = SQLServerConnection.getConnection();  PreparedStatement ps = con.prepareStatement(query);) {
            // Statement: La 1 interface dung de gui cac cau query sang DB
            ps.setObject(1, maGV);
            check = ps.executeUpdate();
        } catch (SQLException e) {
            // SQLException: Dung de bat tat ca loi lien quan toi viec giao tiep DB
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public boolean update(GiangVien gv, String maGV) {
        String query = "UPDATE B7_TrenLop.dbo.giang_vien "
                + "SET ma_gv =?, ten_gv= ? , tuoi=?, bac=?, loai=?, gioi_tinh=? "
                + "WHERE ma_gv= ? ";
        int check = 0;
        try ( Connection con = SQLServerConnection.getConnection();  
                PreparedStatement ps = con.prepareStatement(query);) {
            ps.setObject(1, gv.getMaGV());
            ps.setObject(2, gv.getTenGV());
            ps.setObject(3, gv.getTuoi());
            ps.setObject(4, gv.getBac());
            ps.setObject(5, gv.getLoai());
            ps.setObject(6, gv.isGioiTinh());
            ps.setObject(7, maGV);
            check = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public static void main(String[] args) {
        GiangVien gv = new GiangVien("1111", "aa", 10, "5", "full", true);
        boolean add = new GiangVienRepository().add(gv);
        System.out.println(add);
    }
}
