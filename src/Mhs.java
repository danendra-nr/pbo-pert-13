public class Mhs {
    private int idMhs;
    private String nim;
    private String nama;
    private double nilaiUts;
    private double nilaiUas;
    private double nilaiTugas;

    public Mhs() {}

    public Mhs(int idMhs, String nim, String nama, double nilaiUts, double nilaiUas, double nilaiTugas) {
        this.idMhs = idMhs;
        this.nim = nim;
        this.nama = nama;
        this.nilaiUts = nilaiUts;
        this.nilaiUas = nilaiUas;
        this.nilaiTugas = nilaiTugas;
    }

    public Mhs(String nim, String nama, double nilaiUts, double nilaiUas, double nilaiTugas) {
        this.nim = nim;
        this.nama = nama;
        this.nilaiUts = nilaiUts;
        this.nilaiUas = nilaiUas;
        this.nilaiTugas = nilaiTugas;
    }

    public int getIdMhs() {
        return idMhs;
    }

    public void setIdMhs(int idMhs) {
        this.idMhs = idMhs;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public double uts() {
        return nilaiUts;
    }

    public void setUts(double nilaiUts) {
        this.nilaiUts = nilaiUts;
    }

    public double uas() {
        return nilaiUas;
    }

    public void setUas(double nilaiUas) {
        this.nilaiUas = nilaiUas;
    }

    public double tugas() {
        return nilaiTugas;
    }

    public void setTugas(double nilaiTugas) {
        this.nilaiTugas = nilaiTugas;
    }

    public double nilaiAkhir() {
        return (uts() * 0.35) + (uas() * 0.35) + (tugas() * 0.30);
    }

    public String getNilHuruf() {
        double na = nilaiAkhir();
        if (na >= 80) {
            return "A";
        } else if (na >= 70) {
            return "B";
        } else if (na >= 60) {
            return "C";
        } else if (na >= 50) {
            return "D";
        } else {
            return "E";
        }
    }

    public String getPredikat() {
        String nh = getNilHuruf();
        switch (nh) {
            case "A":
                return "Sangat Memuaskan";
            case "B":
                return "Memuaskan";
            case "C":
                return "Cukup";
            case "D":
                return "Kurang";
            case "E":
            default:
                return "Sangat Kurang";
        }
    }
}
