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

import java.sql.Date;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

/**
 * This class provides Data Access methods for CacheUserAttribute objects
 */
public final class CacheUserAttributeDAO implements ICacheUserAttributeDAO
{
    // Constants
    private static final String SQL_QUERY_SELECTALL = "SELECT id_cache_user_attribute, id_user, id_attribute, content, create_date FROM mylutece_cacheuserattribute_attribute";

    private static final String SQL_QUERY_INSERT = "INSERT INTO mylutece_cacheuserattribute_attribute ( id_user, id_attribute, content, create_date ) VALUES ( ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM mylutece_cacheuserattribute_attribute WHERE id_cache_user_attribute = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE mylutece_cacheuserattribute_attribute SET id_cache_user_attribute = ?, id_user = ?, id_attribute = ?, content = ?, create_date = ? WHERE id_cache_user_attribute = ?";
    private static final String SQL_FILTER_BY_USER_KEY = " WHERE id_user = ? ";
    private static final String SQL_FILTER_BY_ID = " WHERE id_cache_user_attribute = ?";
    private static final String SQL_FILTER_BY_USER_AND_ATTR_ID = " WHERE id_user = ? and id_attribute = ? ";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_cache_user_attribute FROM mylutece_cacheuserattribute_attribute";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( CacheUserAttribute cacheUserAttribute, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin ) )
        {
            int nIndex = 1;
            daoUtil.setString( nIndex++, cacheUserAttribute.getIdUser( ) );
            daoUtil.setInt( nIndex++, cacheUserAttribute.getIdAttribute( ) );
            daoUtil.setString( nIndex++, cacheUserAttribute.getContent( ) );
            daoUtil.setDate( nIndex++, Date.valueOf( cacheUserAttribute.getCreateDate( ) ) );

            daoUtil.executeUpdate( );
            if ( daoUtil.nextGeneratedKey( ) )
            {
                cacheUserAttribute.setId( daoUtil.getGeneratedKeyInt( 1 ) );
            }
        }

    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Optional<CacheUserAttribute> load( int nKey, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL + SQL_FILTER_BY_ID, plugin ) )
        {
            daoUtil.setInt( 1, nKey );
            daoUtil.executeQuery( );
            CacheUserAttribute cacheUserAttribute = null;

            if ( daoUtil.next( ) )
            {
                cacheUserAttribute = new CacheUserAttribute( );
                int nIndex = 1;

                cacheUserAttribute.setId( daoUtil.getInt( nIndex++ ) );
                cacheUserAttribute.setIdUser( daoUtil.getString( nIndex++ ) );
                cacheUserAttribute.setIdAttribute( daoUtil.getInt( nIndex++ ) );
                cacheUserAttribute.setContent( daoUtil.getString( nIndex++ ) );
                cacheUserAttribute.setCreateDate( daoUtil.getDate( nIndex ).toLocalDate( ) );
            }

            return Optional.ofNullable( cacheUserAttribute );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nKey, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin ) )
        {
            daoUtil.setInt( 1, nKey );
            daoUtil.executeUpdate( );
            daoUtil.free( );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store( CacheUserAttribute cacheUserAttribute, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin ) )
        {
            int nIndex = 1;

            daoUtil.setInt( nIndex++, cacheUserAttribute.getId( ) );
            daoUtil.setString( nIndex++, cacheUserAttribute.getIdUser( ) );
            daoUtil.setInt( nIndex++, cacheUserAttribute.getIdAttribute( ) );
            daoUtil.setString( nIndex++, cacheUserAttribute.getContent( ) );
            daoUtil.setDate( nIndex++, Date.valueOf( cacheUserAttribute.getCreateDate( ) ) );
            daoUtil.setInt( nIndex, cacheUserAttribute.getId( ) );

            daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<CacheUserAttribute> selectCacheUserAttributesListByUserKey( String strUserKey, Plugin plugin )
    {
        List<CacheUserAttribute> cacheUserAttributeList = new ArrayList<>( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL + SQL_FILTER_BY_USER_KEY, plugin ) )
        {
            daoUtil.setString( 1, strUserKey );
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                CacheUserAttribute cacheUserAttribute = new CacheUserAttribute( );
                int nIndex = 1;

                cacheUserAttribute.setId( daoUtil.getInt( nIndex++ ) );
                cacheUserAttribute.setIdUser( daoUtil.getString( nIndex++ ) );
                cacheUserAttribute.setIdAttribute( daoUtil.getInt( nIndex++ ) );
                cacheUserAttribute.setContent( daoUtil.getString( nIndex++ ) );
                cacheUserAttribute.setCreateDate( daoUtil.getDate( nIndex ).toLocalDate( ) );

                cacheUserAttributeList.add( cacheUserAttribute );
            }

            return cacheUserAttributeList;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdCacheUserAttributesListByUserKey( String strUserKey, Plugin plugin )
    {
        List<Integer> cacheUserAttributeList = new ArrayList<>( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID + SQL_FILTER_BY_USER_KEY, plugin ) )
        {
            daoUtil.setString( 1, strUserKey );

            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                cacheUserAttributeList.add( daoUtil.getInt( 1 ) );
            }

            return cacheUserAttributeList;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Optional<CacheUserAttribute> loadByUserAndAttributeId( String idUser, int idAttribute, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL + SQL_FILTER_BY_USER_AND_ATTR_ID, plugin ) )
        {
            daoUtil.setString( 1, idUser );
            daoUtil.setInt( 2, idAttribute );
            daoUtil.executeQuery( );
            CacheUserAttribute cacheUserAttribute = null;

            if ( daoUtil.next( ) )
            {
                cacheUserAttribute = new CacheUserAttribute( );
                int nIndex = 1;

                cacheUserAttribute.setId( daoUtil.getInt( nIndex++ ) );
                cacheUserAttribute.setIdUser( daoUtil.getString( nIndex++ ) );
                cacheUserAttribute.setIdAttribute( daoUtil.getInt( nIndex++ ) );
                cacheUserAttribute.setContent( daoUtil.getString( nIndex++ ) );
                cacheUserAttribute.setCreateDate( daoUtil.getDate( nIndex ).toLocalDate( ) );
            }

            return Optional.ofNullable( cacheUserAttribute );
        }
    }

}
