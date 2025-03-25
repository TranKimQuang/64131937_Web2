package edu.quangtk.TongHopGK.Services;

import edu.quangtk.TongHopGK.Models.SinhVien;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SinhVienService {
    private List<SinhVien> dssv = new ArrayList<>();
    private int nextMssv = 1;

    public SinhVienService() {
        // Thêm dữ liệu mẫu
        dssv.add(new SinhVien(nextMssv++, "Nguyen Van A", 8.5f));
        dssv.add(new SinhVien(nextMssv++, "Tran Thi B", 7.8f));
    }

    // Lấy danh sách sinh viên
    public List<SinhVien> getAllSinhVien() {
        return dssv;
    }

    // Thêm sinh viên mới
    public void addSinhVien(SinhVien sinhVien) {
        sinhVien.setMssv(nextMssv++); // Gán giá trị cho mssv
        dssv.add(sinhVien);
    }
}