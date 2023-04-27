package odev;
import java.util.UUID;
public class Motor {
    private String motorNo;
    private boolean calisiyor;
    /**
     * Varsayýlan kurucu fonksiyon
     */
    public Motor() {
        this.motorNo = UUID.randomUUID().toString();
        /* Baþlangýçta false 
         * */calisiyor = false;
    }
    /**
     *
     * @param motorNo UUID olarak motor id
     * @return motor instance
     */
    public Motor(String motorNo) {
        /*
         * Varolan bir motor no ile oluþturuluyor.
         */
        this.motorNo = motorNo;
        calisiyor = false; // false yapýlýyor
    }
    public void calistir() {
/**
 * calisiyor true yapýlýyor
 */
        calisiyor = true;
    }
    /**
     * Motorun durdurulmasý //
     */
    public void durdur() {
        calisiyor = false;
    }
    public String getMotorNo() {
// motor no getir
        return motorNo;
    }
    @Override
    public String toString() {
// durum belirlenmesi //
        String durum = calisiyor ? "Motor Çalýþýyor." : "Motor Çalýþmýyor";
        return durum;
    }
}