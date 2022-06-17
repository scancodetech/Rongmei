package com.rongmei.security.jwt;

import com.rongmei.entity.account.Items;
import com.rongmei.publicdatas.account.Role;
import net.sf.json.JSONObject;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.core.GrantedAuthority;

public class JwtItem implements GrantedAuthority {



    private String itemName;//菜单对应的权限名称

    public JwtItem() {
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    private JwtItem(String itemName) {
        this.itemName = itemName;
    }

    public JwtItem(JSONObject jsonObject) {
        this.itemName = (String) jsonObject.get("name");
    }

    public JwtItem(Items items) {
        this.itemName = items.getAuthority();
    }
    /**
     * If the <code>GrantedAuthority</code> can be represented as a <code>String</code>
     * and that <code>String</code> is sufficient in precision to be relied upon for an
     * access control decision by an {@link AccessDecisionManager} (or delegate), this
     * method should return such a <code>String</code>.
     * <p>
     * If the <code>GrantedAuthority</code> cannot be expressed with sufficient precision
     * as a <code>String</code>, <code>null</code> should be returned. Returning
     * <code>null</code> will require an <code>AccessDecisionManager</code> (or delegate)
     * to specifically support the <code>GrantedAuthority</code> implementation, so
     * returning <code>null</code> should be avoided unless actually required.
     *
     * @return a representation of the granted authority (or <code>null</code> if the
     * granted authority cannot be expressed as a <code>String</code> with sufficient
     * precision).
     */
    @Override
    public String getAuthority() {
        return itemName;
    }
}
