package eu.hackathonovo.data.api.models.response;

public class FilterUsers {

    public final String created_at;
    public final String updated_at;
    public final int id;
    public final String deleted_at;
    public final String username;
    public final String name;
    public final String surname;
    public final String role;
    public final String fcm;

    public FilterUsers(final String created_at, final String updated_at, final int id, final String deleted_at, final String username, final String name, final String surname,
                       final String role, final String fcm) {
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.id = id;
        this.deleted_at = deleted_at;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.fcm = fcm;
    }
}
