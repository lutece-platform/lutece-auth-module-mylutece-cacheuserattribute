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

package fr.paris.lutece.plugins.mylutece.modules.cacheuserattribute.web;

import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.security.SecurityTokenService;
import fr.paris.lutece.portal.service.util.AppException;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.util.url.UrlItem;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import fr.paris.lutece.plugins.mylutece.modules.cacheuserattribute.business.CacheUserAttribute;
import fr.paris.lutece.plugins.mylutece.modules.cacheuserattribute.business.CacheUserAttributeHome;

/**
 * This class provides the user interface to manage CacheUserAttribute features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageCacheUserAttributes.jsp", controllerPath = "jsp/admin/plugins/mylutece/modules/cacheuserattribute/", right = "MYLUTECE_CACHEUSERATTRIBUTE_MANAGEMENT" )
public class CacheUserAttributeJspBean extends AbstractManageCacheUserAttributeJspBean
{

	private static final long serialVersionUID = 5246660343915886385L;
	
	// Templates
    private static final String TEMPLATE_MANAGE_CACHEUSERATTRIBUTES = "/admin/plugins/mylutece/modules/cacheuserattribute/manage_cacheuserattributes.html";
    private static final String TEMPLATE_CREATE_CACHEUSERATTRIBUTE = "/admin/plugins/mylutece/modules/cacheuserattribute/create_cacheuserattribute.html";
    private static final String TEMPLATE_MODIFY_CACHEUSERATTRIBUTE = "/admin/plugins/mylutece/modules/cacheuserattribute/modify_cacheuserattribute.html";

    // Parameters
    private static final String PARAMETER_ID_CACHEUSERATTRIBUTE = "id";
    private static final String PARAMETER_USER_ID = "user_id";
    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_CACHEUSERATTRIBUTES = "module.mylutece.cacheuserattribute.manage_cacheuserattributes.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_CACHEUSERATTRIBUTE = "module.mylutece.cacheuserattribute.modify_cacheuserattribute.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_CACHEUSERATTRIBUTE = "module.mylutece.cacheuserattribute.create_cacheuserattribute.pageTitle";

    // Markers
    private static final String MARK_CACHEUSERATTRIBUTE_LIST = "cacheuserattribute_list";
    private static final String MARK_CACHEUSERATTRIBUTE = "cacheuserattribute";

    private static final String JSP_MANAGE_CACHEUSERATTRIBUTES = "jsp/admin/plugins/mylutece/modules/cacheuserattribute/ManageCacheUserAttributes.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_CACHEUSERATTRIBUTE = "module.mylutece.cacheuserattribute.message.confirmRemoveCacheUserAttribute";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "module.mylutece.cacheuserattribute.model.entity.cacheuserattribute.attribute.";

    // Views
    private static final String VIEW_MANAGE_CACHEUSERATTRIBUTES = "manageCacheUserAttributes";
    private static final String VIEW_CREATE_CACHEUSERATTRIBUTE = "createCacheUserAttribute";
    private static final String VIEW_MODIFY_CACHEUSERATTRIBUTE = "modifyCacheUserAttribute";

    // Actions
    private static final String ACTION_CREATE_CACHEUSERATTRIBUTE = "createCacheUserAttribute";
    private static final String ACTION_MODIFY_CACHEUSERATTRIBUTE = "modifyCacheUserAttribute";
    private static final String ACTION_REMOVE_CACHEUSERATTRIBUTE = "removeCacheUserAttribute";
    private static final String ACTION_CONFIRM_REMOVE_CACHEUSERATTRIBUTE = "confirmRemoveCacheUserAttribute";

    // Infos
    private static final String INFO_CACHEUSERATTRIBUTE_CREATED = "module.mylutece.cacheuserattribute.info.cacheuserattribute.created";
    private static final String INFO_CACHEUSERATTRIBUTE_UPDATED = "module.mylutece.cacheuserattribute.info.cacheuserattribute.updated";
    private static final String INFO_CACHEUSERATTRIBUTE_REMOVED = "module.mylutece.cacheuserattribute.info.cacheuserattribute.removed";

    // ERRORS
    private static final String ERROR_RESOURCE_NOT_FOUND = "Resource not found";

    // Session variable to store working values
    private CacheUserAttribute _cacheuserattribute;

    /**
     * Build the Manage View
     * 
     * @param request
     *            The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_CACHEUSERATTRIBUTES, defaultView = true )
    public String getManageCacheUserAttributes( HttpServletRequest request )
    {
        _cacheuserattribute = null;

        String strUserId = request.getParameter( PARAMETER_USER_ID );

        List<CacheUserAttribute> listCacheUserAttributes = CacheUserAttributeHome.getCacheUserAttributesListByUserKey( strUserId );
        Map<String, Object> model = getPaginatedListModel( request, MARK_CACHEUSERATTRIBUTE_LIST, listCacheUserAttributes, JSP_MANAGE_CACHEUSERATTRIBUTES );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_CACHEUSERATTRIBUTES, TEMPLATE_MANAGE_CACHEUSERATTRIBUTES, model );
    }

    /**
     * Returns the form to create a cacheuserattribute
     *
     * @param request
     *            The Http request
     * @return the html code of the cacheuserattribute form
     */
    @View( VIEW_CREATE_CACHEUSERATTRIBUTE )
    public String getCreateCacheUserAttribute( HttpServletRequest request )
    {
        _cacheuserattribute = ( _cacheuserattribute != null ) ? _cacheuserattribute : new CacheUserAttribute( );

        Map<String, Object> model = getModel( );
        model.put( MARK_CACHEUSERATTRIBUTE, _cacheuserattribute );
        model.put( SecurityTokenService.MARK_TOKEN, SecurityTokenService.getInstance( ).getToken( request, ACTION_CREATE_CACHEUSERATTRIBUTE ) );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_CACHEUSERATTRIBUTE, TEMPLATE_CREATE_CACHEUSERATTRIBUTE, model );
    }

    /**
     * Process the data capture form of a new cacheuserattribute
     *
     * @param request
     *            The Http Request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_CREATE_CACHEUSERATTRIBUTE )
    public String doCreateCacheUserAttribute( HttpServletRequest request ) throws AccessDeniedException
    {
        populate( _cacheuserattribute, request, getLocale( ) );

        if ( !SecurityTokenService.getInstance( ).validate( request, ACTION_CREATE_CACHEUSERATTRIBUTE ) )
        {
            throw new AccessDeniedException( "Invalid security token" );
        }

        // Check constraints
        if ( !validateBean( _cacheuserattribute, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_CACHEUSERATTRIBUTE );
        }

        CacheUserAttributeHome.create( _cacheuserattribute );
        addInfo( INFO_CACHEUSERATTRIBUTE_CREATED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_CACHEUSERATTRIBUTES );
    }

    /**
     * Manages the removal form of a cacheuserattribute whose identifier is in the http request
     *
     * @param request
     *            The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_CACHEUSERATTRIBUTE )
    public String getConfirmRemoveCacheUserAttribute( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_CACHEUSERATTRIBUTE ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_CACHEUSERATTRIBUTE ) );
        url.addParameter( PARAMETER_ID_CACHEUSERATTRIBUTE, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_CACHEUSERATTRIBUTE, url.getUrl( ),
                AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a cacheuserattribute
     *
     * @param request
     *            The Http request
     * @return the jsp URL to display the form to manage cacheuserattributes
     */
    @Action( ACTION_REMOVE_CACHEUSERATTRIBUTE )
    public String doRemoveCacheUserAttribute( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_CACHEUSERATTRIBUTE ) );

        CacheUserAttributeHome.remove( nId );
        addInfo( INFO_CACHEUSERATTRIBUTE_REMOVED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_CACHEUSERATTRIBUTES );
    }

    /**
     * Returns the form to update info about a cacheuserattribute
     *
     * @param request
     *            The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_CACHEUSERATTRIBUTE )
    public String getModifyCacheUserAttribute( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_CACHEUSERATTRIBUTE ) );

        if ( _cacheuserattribute == null || ( _cacheuserattribute.getId( ) != nId ) )
        {
            _cacheuserattribute = CacheUserAttributeHome.findByPrimaryKey( nId ).orElseThrow( ( ) -> new AppException( ERROR_RESOURCE_NOT_FOUND ) );
        }

        Map<String, Object> model = getModel( );
        model.put( MARK_CACHEUSERATTRIBUTE, _cacheuserattribute );
        model.put( SecurityTokenService.MARK_TOKEN, SecurityTokenService.getInstance( ).getToken( request, ACTION_MODIFY_CACHEUSERATTRIBUTE ) );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_CACHEUSERATTRIBUTE, TEMPLATE_MODIFY_CACHEUSERATTRIBUTE, model );
    }

    /**
     * Process the change form of a cacheuserattribute
     *
     * @param request
     *            The Http request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_MODIFY_CACHEUSERATTRIBUTE )
    public String doModifyCacheUserAttribute( HttpServletRequest request ) throws AccessDeniedException
    {
        populate( _cacheuserattribute, request, getLocale( ) );

        if ( !SecurityTokenService.getInstance( ).validate( request, ACTION_MODIFY_CACHEUSERATTRIBUTE ) )
        {
            throw new AccessDeniedException( "Invalid security token" );
        }

        // Check constraints
        if ( !validateBean( _cacheuserattribute, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_CACHEUSERATTRIBUTE, PARAMETER_ID_CACHEUSERATTRIBUTE, _cacheuserattribute.getId( ) );
        }

        CacheUserAttributeHome.update( _cacheuserattribute );
        addInfo( INFO_CACHEUSERATTRIBUTE_UPDATED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_CACHEUSERATTRIBUTES );
    }
}
