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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import fr.paris.lutece.plugins.mylutece.modules.cacheuserattribute.business.CacheUserAttribute;
import fr.paris.lutece.plugins.mylutece.modules.cacheuserattribute.business.CacheUserAttributeHome;
import fr.paris.lutece.portal.business.event.LuteceUserEvent;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

@ApplicationScoped
public class CacheUserAttributeService
{

    private static final String PROPERTY_CACHE_ATTRIBUTES_PREFIX = "mylutece-cacheuserattribute.attributeIds";

    private static final int FIRST_CONNECTION_DATE_ATTRIBUTE_ID = 0;
    private static final String FIRST_CONNECTION_ATTRIBUTE_CONTENT = "init";

    private Map<String, Integer> _attributeIdByPropertyKey;
    private Map<Integer, String> _propertyKeyByAttributeId;

    /**
     * Initialization of the maps
     */
    @PostConstruct
    public void init( )
    {
        _attributeIdByPropertyKey = new HashMap<>( );
        for ( String key : AppPropertiesService.getKeys( PROPERTY_CACHE_ATTRIBUTES_PREFIX ) )
        {
            _attributeIdByPropertyKey.put( key, AppPropertiesService.getPropertyInt( key, -1 ) );
        }

        _propertyKeyByAttributeId = new HashMap<>( );
        for ( String key : _attributeIdByPropertyKey.keySet( ) )
        {
            _propertyKeyByAttributeId.put( AppPropertiesService.getPropertyInt( key, -1 ), key );
        }

        AppLogService.info( "CacheUserAttribute module initialized" );
    }

    /**
     * Observe Lutece user login events via CDI
     * 
     * @param event the Lutece user event
     */
    public void onLuteceUserLogin( @Observes LuteceUserEvent event )
    {
        if ( event.getParam( ) != null && event.getType( ) == LuteceUserEvent.EventType.LOGIN_SUCCESSFUL )
        {
            AppLogService.debug( "listener says > " + event.getParam( ).getName( ) + " performed : " + event.getType( ) );

            loginEvent( event );
        }
    }

    /**
     * consume event
     * 
     * @param event
     */
    public void loginEvent( LuteceUserEvent event )
    {
        LuteceUser user = event.getParam( );

        AppLogService.debug( user.getUserInfos( ).keySet( ) );

        boolean mustBeCached = false;

        for ( String userAttrKey : user.getUserInfos( ).keySet( ) )
        {
            if ( _attributeIdByPropertyKey.containsKey( PROPERTY_CACHE_ATTRIBUTES_PREFIX + "." + userAttrKey ) )
            {
                mustBeCached = true;

                CacheUserAttribute attr = new CacheUserAttribute( );
                attr.setIdUser( user.getAccessCode( ) );
                attr.setIdAttribute( _attributeIdByPropertyKey.get( PROPERTY_CACHE_ATTRIBUTES_PREFIX + "." + userAttrKey ) );
                attr.setContent( user.getUserInfo( userAttrKey ) );

                CacheUserAttributeHome.createOrUpdateIfDifferent( attr );
            }
        }

        if ( mustBeCached )
        {
            // store first cache creation date
            if ( !CacheUserAttributeHome.findByUserAndAttributeId( user.getAccessCode( ), FIRST_CONNECTION_DATE_ATTRIBUTE_ID ).isPresent( ) )
            {
                CacheUserAttribute attr = new CacheUserAttribute( );
                attr.setIdUser( user.getAccessCode( ) );
                attr.setIdAttribute( FIRST_CONNECTION_DATE_ATTRIBUTE_ID );
                attr.setContent( FIRST_CONNECTION_ATTRIBUTE_CONTENT );

                CacheUserAttributeHome.create( attr );
            }
        }
    }

    /**
     * get cache attributes as Map of a lutece user
     * 
     * @param strUserId
     * @return the map
     */
    public Map<String, String> getCachedAttributes( String strUserId )
    {
        List<CacheUserAttribute> attrList = CacheUserAttributeHome.getCacheUserAttributesListByUserKey( strUserId );

        Map<String, String> attrMap = new HashMap<>( );

        for ( CacheUserAttribute attr : attrList )
        {
            attrMap.put( _propertyKeyByAttributeId.get( attr.getIdAttribute( ) ), attr.getContent( ) );
        }

        return attrMap;
    }

}
