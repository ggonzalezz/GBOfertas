package gb.gerson.gb;

public class Subir {


    private  Integer id;
    private  String uid;
    private String name;
    private String ImagenUri;
    private String description;



    public Subir() {
    }

    public Subir(String name, String imagenUri, String description, int id, String uid) {
        if (name.trim().equals("") && description.trim().equals(""))
        {
            name = "No hay Nombre";
            description = "No hay Descripcion";

        }
        //this.Uid = uid;
        this.id = id;
        this.uid = uid;
        this.name = name;
        this.ImagenUri = imagenUri;
        this.description = description;
        //this.mDescription =  description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagenUri() {
        return ImagenUri;
    }

    public void setImagenUri(String imagenUri) {
        ImagenUri = imagenUri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
