package util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Tiện ích định dạng TIỀN TỆ dùng chung cho toàn hệ thống TNT.
 *
 * Lý do có lớp này: EL của JSP in kiểu double ra màn hình theo Double.toString()
 * nên 25000000.0 sẽ hiển thị thành "2.5E7" (lỗi hiển thị hay gặp). Ngoài ra
 * fmt:formatNumber lại phụ thuộc Locale mặc định của máy chủ nên có máy ra
 * "1,000,000", có máy ra "1.000.000".
 *
 * Lớp này ép cứng ký hiệu phân cách của Việt Nam (dấu chấm cho hàng nghìn,
 * dấu phẩy cho thập phân) nên chạy máy nào cũng cho ra kết quả giống nhau:
 * 1000000 -> "1.000.000 VNĐ".
 *
 * Cách dùng trong JSP (đã khai báo taglib tnt):
 * ${tnt:vnd(sp.donGiaBan)} -> 25.000.000 VNĐ
 * ${tnt:so(sp.donGiaBan)}  -> 25.000.000
 * ${tnt:raw(sp.donGiaBan)} -> 25000000     (dùng cho input type="number")
 */
public final class TienTe {

    /**
     * Đơn vị tiền tệ hiển thị. Muốn đổi sang "VND" hoặc "đ" thì chỉ cần sửa
     * duy nhất dòng này, toàn bộ hệ thống sẽ đổi theo.
     */
    public static final String DON_VI = "VNĐ";

    /** Ký hiệu kiểu Việt Nam: 1.000.000,5 */
    private static final DecimalFormatSymbols KY_HIEU_VN = taoKyHieuVN();

    /** Ký hiệu kiểu máy (dấu chấm thập phân) dùng cho input/JavaScript. */
    private static final DecimalFormatSymbols KY_HIEU_MAY = new DecimalFormatSymbols(Locale.US);

    private TienTe() {
        // Lớp tiện ích, không cho khởi tạo.
    }

    private static DecimalFormatSymbols taoKyHieuVN() {
        DecimalFormatSymbols kyHieu = new DecimalFormatSymbols(Locale.US);
        kyHieu.setGroupingSeparator('.');
        kyHieu.setDecimalSeparator(',');
        return kyHieu;
    }

    /**
     * Định dạng số có phân cách hàng nghìn kiểu Việt Nam (không kèm đơn vị).
     * VD: 1000000 -> "1.000.000"
     */
    public static String so(double giaTri) {
        if (Double.isNaN(giaTri) || Double.isInfinite(giaTri)) {
            return "0";
        }
        // Tránh trường hợp -0 do làm tròn các giá trị rất nhỏ.
        if (Math.abs(giaTri) < 0.5d) {
            giaTri = 0d;
        }
        return new DecimalFormat("#,##0", KY_HIEU_VN).format(giaTri);
    }

    /**
     * Định dạng tiền tệ đầy đủ kèm đơn vị.
     * VD: 1000000 -> "1.000.000 VNĐ"
     */
    public static String vnd(double giaTri) {
        return so(giaTri) + " " + DON_VI;
    }

    /**
     * Định dạng tiền tệ có dấu +/- ở đầu, dùng cho cột lãi/lỗ, tăng/giảm.
     * VD: 1000000 -> "+1.000.000 VNĐ" ; -500000 -> "-500.000 VNĐ"
     */
    public static String vndCoDau(double giaTri) {
        String dau = giaTri > 0 ? "+" : "";
        return dau + vnd(giaTri);
    }

    /**
     * Giá trị "thô" không phân cách, dùng cho input type="number",
     * thuộc tính data-* hoặc mảng dữ liệu JavaScript.
     * VD: 25000000.0 -> "25000000"
     */
    public static String raw(double giaTri) {
        if (Double.isNaN(giaTri) || Double.isInfinite(giaTri)) {
            return "0";
        }
        return new DecimalFormat("0.##", KY_HIEU_MAY).format(giaTri);
    }

    /**
     * Định dạng phần trăm kiểu Việt Nam.
     * VD: 12.3456 -> "12,35%"
     */
    public static String phanTram(double giaTri) {
        if (Double.isNaN(giaTri) || Double.isInfinite(giaTri)) {
            return "0%";
        }
        return new DecimalFormat("#,##0.##", KY_HIEU_VN).format(giaTri) + "%";
    }

    /**
     * Rút gọn số tiền lớn cho thẻ KPI / nhãn biểu đồ.
     * VD: 1500000 -> "1,5 Tr" ; 2300000000 -> "2,3 Tỷ"
     */
    public static String rutGon(double giaTri) {
        double abs = Math.abs(giaTri);
        DecimalFormat dinhDang = new DecimalFormat("#,##0.#", KY_HIEU_VN);
        if (abs >= 1_000_000_000d) {
            return dinhDang.format(giaTri / 1_000_000_000d) + " Tỷ";
        }
        if (abs >= 1_000_000d) {
            return dinhDang.format(giaTri / 1_000_000d) + " Tr";
        }
        if (abs >= 1_000d) {
            return dinhDang.format(giaTri / 1_000d) + " N";
        }
        return so(giaTri);
    }

    /**
     * Rút gọn kèm đơn vị, dùng cho thẻ tổng quan.
     * VD: 1500000 -> "1,5 Tr VNĐ"
     */
    public static String rutGonVnd(double giaTri) {
        return rutGon(giaTri) + " " + DON_VI;
    }
}
