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

import javax.validation.constraints.Size;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

/**
 * This is the business class for the object CacheUserAttribute
 */
public class CacheUserAttribute implements Serializable
{
    private static final long serialVersionUID = 1L;

    // Variables declarations
    private int _nId;

    @NotEmpty( message = "#i18n{module.mylutece.cacheuserattribute.validation.cacheuserattribute.IdUser.notEmpty}" )
    @Size( max = 50, message = "#i18n{module.mylutece.cacheuserattribute.validation.cacheuserattribute.IdUser.size}" )
    private String _strIdUser;

    private int _nIdAttribute;

    @Size( max = 50, message = "#i18n{module.mylutece.cacheuserattribute.validation.cacheuserattribute.Content.size}" )
    private String _strContent;
    @NotNull( message = "#i18n{portal.validation.message.notEmpty}" )
    private LocalDate _dateCreateDate;

    /**
     * Returns the Id
     * 
     * @return The Id
     */
    public int getId( )
    {
        return _nId;
    }

    /**
     * Sets the Id
     * 
     * @param nId
     *            The Id
     */
    public void setId( int nId )
    {
        _nId = nId;
    }

    /**
     * Returns the IdUser
     * 
     * @return The IdUser
     */
    public String getIdUser( )
    {
        return _strIdUser;
    }

    /**
     * Sets the IdUser
     * 
     * @param strIdUser
     *            The IdUser
     */
    public void setIdUser( String strIdUser )
    {
        _strIdUser = strIdUser;
    }

    /**
     * Returns the IdAttribute
     * 
     * @return The IdAttribute
     */
    public int getIdAttribute( )
    {
        return _nIdAttribute;
    }

    /**
     * Sets the IdAttribute
     * 
     * @param nIdAttribute
     *            The IdAttribute
     */
    public void setIdAttribute( int nIdAttribute )
    {
        _nIdAttribute = nIdAttribute;
    }

    /**
     * Returns the Content
     * 
     * @return The Content
     */
    public String getContent( )
    {
        return _strContent;
    }

    /**
     * Sets the Content
     * 
     * @param strContent
     *            The Content
     */
    public void setContent( String strContent )
    {
        _strContent = strContent;
    }

    /**
     * Returns the CreateDate
     * 
     * @return The CreateDate
     */
    public LocalDate getCreateDate( )
    {
        return _dateCreateDate;
    }

    /**
     * Sets the CreateDate
     * 
     * @param dateCreateDate
     *            The CreateDate
     */
    public void setCreateDate( LocalDate dateCreateDate )
    {
        _dateCreateDate = dateCreateDate;
    }

}
