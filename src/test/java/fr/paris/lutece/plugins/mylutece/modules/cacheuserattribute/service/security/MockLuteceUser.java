package fr.paris.lutece.plugins.mylutece.modules.cacheuserattribute.service.security;

import fr.paris.lutece.portal.service.security.LuteceAuthentication;
import fr.paris.lutece.portal.service.security.LuteceUser;

/**
 * MockLuteceUser
 */
public class MockLuteceUser extends LuteceUser
{
    /** Creates a new instance of MockLuteceUser */
    public MockLuteceUser( String strName, LuteceAuthentication auth )
    {
        super( strName, auth );
    }
}
