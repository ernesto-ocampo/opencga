package org.opencb.opencga.catalog.models.acls.permissions;

import java.util.EnumSet;

/**
 * Created by pfurio on 04/07/16.
 */
public abstract class AbstractAclEntry<E extends Enum<E>> {

    public static final String USER_OTHERS_ID = "*";

    protected String member;
    protected EnumSet<E> permissions;

    public AbstractAclEntry() {
        this("", null);
    }

    public AbstractAclEntry(String member, EnumSet<E> permissions) {
        this.member = member;
        this.permissions = permissions;
    }

    public String getMember() {
        return member;
    }

    public AbstractAclEntry setMember(String member) {
        this.member = member;
        return this;
    }

    public EnumSet<E> getPermissions() {
        return permissions;
    }

    public AbstractAclEntry setPermissions(EnumSet<E> permissions) {
        this.permissions = permissions;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AclEntry{");
        sb.append("member='").append(member).append('\'');
        sb.append(", permissions=").append(permissions);
        sb.append('}');
        return sb.toString();
    }
}
