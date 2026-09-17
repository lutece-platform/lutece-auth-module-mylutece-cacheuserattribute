/*
 * Copyright (c) 2002-2022, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.mylutece.modules.cacheuserattribute.service;

import java.lang.reflect.Proxy;

import fr.paris.lutece.portal.business.event.LuteceUserEvent;
import fr.paris.lutece.portal.service.security.LuteceAuthentication;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.test.LuteceTestCase;

import org.junit.jupiter.api.Test;

public class CacheUserAttributeServiceTest extends LuteceTestCase
{

 	private static final String USER_NAME = "user-test";

 	/**
     * Minimal LuteceUser for tests (core test-jar Moke classes are not on the plugin test classpath in v8)
     */
    private static class TestLuteceUser extends LuteceUser
    {
        TestLuteceUser( String strName, LuteceAuthentication auth )
        {
            super( strName, auth );
        }
    }

 	@Test
	public void test( )
    {
        // Minimal authentication stub: only getAuthServiceName is used by the LuteceUser constructor
        LuteceAuthentication auth = (LuteceAuthentication) Proxy.newProxyInstance( getClass( ).getClassLoader( ),
                new Class<?> [ ] {
                    LuteceAuthentication.class
                }, ( proxy, method, args ) -> {
                    if ( "getAuthServiceName".equals( method.getName( ) ) )
                    {
                        return "TEST";
                    }
                    Class<?> returnType = method.getReturnType( );
                    if ( boolean.class.equals( returnType ) )
                    {
                        return false;
                    }
                    if ( int.class.equals( returnType ) )
                    {
                        return 0;
                    }
                    return null;
                } );
        LuteceUser user = new TestLuteceUser( USER_NAME, auth );

    	// notify an event
    	CacheUserAttributeService.loginEvent( new LuteceUserEvent( user, LuteceUserEvent.EventType.LOGIN_SUCCESSFUL ) );


    }
    
}
