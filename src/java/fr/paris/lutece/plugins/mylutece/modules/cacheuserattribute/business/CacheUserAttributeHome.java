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

package fr.paris.lutece.plugins.mylutece.modules.cacheuserattribute.business;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;

/**
 * This class provides instances management methods (create, find, ...) for CacheUserAttribute objects
 */
public final class CacheUserAttributeHome
{
    // Static variable pointed at the DAO instance
    private static ICacheUserAttributeDAO _dao = SpringContextService.getBean( "mylutece-cacheuserattribute.cacheUserAttributeDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "mylutece-cacheuserattribute" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private CacheUserAttributeHome( )
    {
    }

    /**
     * Create an instance of the cacheUserAttribute class
     * 
     * @param cacheUserAttribute
     *            The instance of the CacheUserAttribute which contains the informations to store
     * @return The instance of cacheUserAttribute which has been created with its primary key.
     */
    public static CacheUserAttribute create( CacheUserAttribute cacheUserAttribute )
    {
        cacheUserAttribute.setCreateDate( LocalDate.now( ) );
        _dao.insert( cacheUserAttribute, _plugin );

        return cacheUserAttribute;
    }

    /**
     * Create an instance of the cacheUserAttribute if not exists or update the data if different
     * 
     * @param cacheUserAttribute
     *            The instance of the CacheUserAttribute which contains the informations to store
     * @return The instance of cacheUserAttribute which has been created with its primary key.
     */
    public static CacheUserAttribute createOrUpdateIfDifferent( CacheUserAttribute cacheUserAttribute )
    {
        // search if attribute already present in cache
        Optional<CacheUserAttribute> optStoredAttribute = _dao.loadByUserAndAttributeId( cacheUserAttribute.getIdUser( ), cacheUserAttribute.getIdAttribute( ),
                _plugin );

        if ( optStoredAttribute.isPresent( ) )
        {
            CacheUserAttribute storedAttribute = optStoredAttribute.get( );

            if ( !storedAttribute.getContent( ).equals( cacheUserAttribute.getContent( ) ) )
            {
                // update attribute cache value
                cacheUserAttribute.setId( storedAttribute.getId( ) );
                cacheUserAttribute.setCreateDate( LocalDate.now( ) );

                _dao.store( cacheUserAttribute, _plugin );
            }
        }
        else
        {
            // create attribute cache if not exists
            cacheUserAttribute.setCreateDate( LocalDate.now( ) );
            _dao.insert( cacheUserAttribute, _plugin );
        }

        return cacheUserAttribute;
    }

    /**
     * Update of the cacheUserAttribute which is specified in parameter
     * 
     * @param cacheUserAttribute
     *            The instance of the CacheUserAttribute which contains the data to store
     * @return The instance of the cacheUserAttribute which has been updated
     */
    public static CacheUserAttribute update( CacheUserAttribute cacheUserAttribute )
    {
        _dao.store( cacheUserAttribute, _plugin );

        return cacheUserAttribute;
    }

    /**
     * Remove the cacheUserAttribute whose identifier is specified in parameter
     * 
     * @param nKey
     *            The cacheUserAttribute Id
     */
    public static void remove( int nKey )
    {
        _dao.delete( nKey, _plugin );
    }

    /**
     * Returns an instance of a cacheUserAttribute whose identifier is specified in parameter
     * 
     * @param nKey
     *            The cacheUserAttribute primary key
     * @return an instance of CacheUserAttribute
     */
    public static Optional<CacheUserAttribute> findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }

    /**
     * Returns an instance of a cacheUserAttribute whose identifier is specified in parameter
     * 
     * @param nKey
     *            The cacheUserAttribute primary key
     * @return an instance of CacheUserAttribute
     */
    public static Optional<CacheUserAttribute> findByUserAndAttributeId( String strUserId, int nAttrId )
    {
        return _dao.loadByUserAndAttributeId( strUserId, nAttrId, _plugin );
    }

    /**
     * Load the data of all the cacheUserAttribute objects and returns them as a list
     * 
     * @return the list which contains the data of all the cacheUserAttribute objects
     */
    public static List<CacheUserAttribute> getCacheUserAttributesListByUserKey( String strUserKey )
    {
        return _dao.selectCacheUserAttributesListByUserKey( strUserKey, _plugin );
    }

    /**
     * Load the id of all the cacheUserAttribute objects and returns them as a list
     * 
     * @return the list which contains the id of all the cacheUserAttribute objects
     */
    public static List<Integer> getIdCacheUserAttributesList( String strUserKey )
    {
        return _dao.selectIdCacheUserAttributesListByUserKey( strUserKey, _plugin );
    }
}
