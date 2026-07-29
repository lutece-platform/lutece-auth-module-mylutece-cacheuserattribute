package fr.paris.lutece.plugins.mylutece.modules.cacheuserattribute.service.security;

import java.util.Arrays;
import java.util.Collection;

import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;

import jakarta.servlet.http.HttpServletRequest;

import fr.paris.lutece.portal.service.security.LuteceAuthentication;
import fr.paris.lutece.portal.service.security.LuteceUser;

/**
 * MockLuteceAuthentication
 */
public class MockLuteceAuthentication implements LuteceAuthentication
{
    /** Creates a new instance of MockLuteceAuthentication */
    public MockLuteceAuthentication( )
    {
    }

    public String getAuthServiceName( )
    {
        return "MOKE AUTHENTICATION SERVICE";
    }

    public String getAuthType( HttpServletRequest request )
    {
        return null;
    }

    public LuteceUser login( final String strUserName, final String strUserPassword, HttpServletRequest request ) throws LoginException
    {
        if ( "admin".equals( strUserName ) && "adminadmin".equals( strUserPassword ) )
        {
            return new MockLuteceUser( strUserName, this );
        }
        else
        {
            throw new FailedLoginException( );
        }
    }

    public void logout( LuteceUser user )
    {
    }

    public boolean findResetPassword( HttpServletRequest request, String strLogin )
    {
        return false;
    }

    public LuteceUser getAnonymousUser( )
    {
        return null;
    }

    public boolean isUserInRole( LuteceUser user, HttpServletRequest request, String strRole )
    {
        return Arrays.asList( user.getRoles( ) ).contains( strRole );
    }

    public String [ ] getRolesByUser( LuteceUser user )
    {
        return null;
    }

    public boolean isExternalAuthentication( )
    {
        return false;
    }

    public LuteceUser getHttpAuthenticatedUser( HttpServletRequest request )
    {
        return null;
    }

    public String getLoginPageUrl( )
    {
        return null;
    }

    public String getDoLoginUrl( )
    {
        return null;
    }

    public String getDoLogoutUrl( )
    {
        return null;
    }

    public String getNewAccountPageUrl( )
    {
        return null;
    }

    public String getViewAccountPageUrl( )
    {
        return null;
    }

    public String getLostPasswordPageUrl( )
    {
        return null;
    }

    public String getResetPasswordPageUrl( HttpServletRequest request )
    {
        return null;
    }

    public String getAccessDeniedTemplate( )
    {
        return null;
    }

    public String getAccessControledTemplate( )
    {
        return null;
    }

    public boolean isUsersListAvailable( )
    {
        return false;
    }

    public Collection<LuteceUser> getUsers( )
    {
        return null;
    }

    public LuteceUser getUser( String strUserLogin )
    {
        return null;
    }

    public boolean isDelegatedAuthentication( )
    {
        return false;
    }

    public boolean isMultiAuthenticationSupported( )
    {
        return false;
    }

    public String getIconUrl( )
    {
        return null;
    }

    public String getName( )
    {
        return null;
    }

    public String getPluginName( )
    {
        return null;
    }

    public void updateDateLastLogin( LuteceUser user, HttpServletRequest request )
    {
    }

    @Override
    public String getLostLoginPageUrl( )
    {
        return null;
    }
}
