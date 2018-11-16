package cua.comfirebaseapp.firebaseapp;

public class Notas {
    public String id;
    public String nombre;
    public int count;

    public Notas() {}

    public Notas(String id, String nombre, int count) {
        this.id = id;
        this.nombre = nombre;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
