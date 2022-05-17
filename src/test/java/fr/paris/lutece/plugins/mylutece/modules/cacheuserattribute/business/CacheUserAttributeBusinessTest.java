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

import fr.paris.lutece.portal.service.util.AppException;
import fr.paris.lutece.test.LuteceTestCase;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

/**
 * This is the business class test for the object CacheUserAttribute
 */
public class CacheUserAttributeBusinessTest extends LuteceTestCase
{
    private static final String IDUSER1 = "IdUser1";
    private static final String IDUSER2 = "IdUser2";
    private static final int IDATTRIBUTE1 = 1;
    private static final int IDATTRIBUTE2 = 2;
    private static final String CONTENT1 = "Content1";
    private static final String CONTENT2 = "Content2";
    private static final LocalDate CREATEDATE1 = LocalDate.now( );
    private static final LocalDate CREATEDATE2 = LocalDate.now( ).plusDays( 1 );

    /**
     * test CacheUserAttribute
     */
    public void testBusiness( )
    {
        // Initialize an object
        CacheUserAttribute cacheUserAttribute = new CacheUserAttribute( );
        cacheUserAttribute.setIdUser( IDUSER1 );
        cacheUserAttribute.setIdAttribute( IDATTRIBUTE1 );
        cacheUserAttribute.setContent( CONTENT1 );
        cacheUserAttribute.setCreateDate( CREATEDATE1 );

        // Create test
        CacheUserAttributeHome.create( cacheUserAttribute );
        CacheUserAttribute cacheUserAttributeStored = CacheUserAttributeHome.findByPrimaryKey( cacheUserAttribute.getId( ) )
                .orElseThrow( ( ) -> new AppException( "resource not found" ) );
        assertEquals( cacheUserAttributeStored.getIdUser( ), cacheUserAttribute.getIdUser( ) );
        assertEquals( cacheUserAttributeStored.getIdAttribute( ), cacheUserAttribute.getIdAttribute( ) );
        assertEquals( cacheUserAttributeStored.getContent( ), cacheUserAttribute.getContent( ) );
        assertEquals( cacheUserAttributeStored.getCreateDate( ).toString( ), cacheUserAttribute.getCreateDate( ).toString( ) );

        // Update test
        cacheUserAttribute.setIdUser( IDUSER2 );
        cacheUserAttribute.setIdAttribute( IDATTRIBUTE2 );
        cacheUserAttribute.setContent( CONTENT2 );
        cacheUserAttribute.setCreateDate( CREATEDATE2 );
        CacheUserAttributeHome.update( cacheUserAttribute );
        
        assertTrue( CacheUserAttributeHome.findByPrimaryKey( cacheUserAttribute.getId( ) ).isPresent( ) );
        
        cacheUserAttributeStored = CacheUserAttributeHome.findByPrimaryKey( cacheUserAttribute.getId( ) )
                .orElseThrow( ( ) -> new AppException( "resource not found" ) );
        assertEquals( cacheUserAttributeStored.getIdUser( ), cacheUserAttribute.getIdUser( ) );
        assertEquals( cacheUserAttributeStored.getIdAttribute( ), cacheUserAttribute.getIdAttribute( ) );
        assertEquals( cacheUserAttributeStored.getContent( ), cacheUserAttribute.getContent( ) );
        assertEquals( cacheUserAttributeStored.getCreateDate( ).toString( ), cacheUserAttribute.getCreateDate( ).toString( ) );

        // List test
        CacheUserAttributeHome.getCacheUserAttributesListByUserKey( IDUSER2 );

        // Delete test
        CacheUserAttributeHome.remove( cacheUserAttribute.getId( ) );
        assertFalse( CacheUserAttributeHome.findByPrimaryKey( cacheUserAttribute.getId( ) ).isPresent( ) );

    }

}
