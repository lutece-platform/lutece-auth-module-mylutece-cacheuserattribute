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
package fr.paris.lutece.plugins.mylutece.modules.cacheuserattribute.dashboard;

import java.util.HashMap;
import java.util.Map;

import fr.paris.lutece.plugins.mylutece.modules.cacheuserattribute.service.CacheUserAttributeService;
import fr.paris.lutece.portal.service.dashboard.IPublicDashboardComponent;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.html.HtmlTemplate;

/**
 * The Class MyPublicUserInfoProfile.
 */
public class PublicDashboardUserInfo implements IPublicDashboardComponent
{
	public static final String DASHBOARD_PROPERTIES_TITLE = "module.mylutece.cacheuserattribute.publicdashboard.bean.title";
	private String strIdComponent = "mylutece-cacheuserattribute.PublicDashboardUserInfo";
	private static final String TEMPLATE_USER_INFORMATIONS = "/skin/plugins/publicdashboard/view_user_informations.html";
	private static final String MARK_DASHBOARD_USER = "dashboardUserInformations";

	@Override
	public String getDashboardData( String user_id, Map<String,String> additionalParameters ) {
		Map<String, Object> model = new HashMap<String, Object>( );
		model.put( MARK_DASHBOARD_USER, searchUserInformations( user_id ) );
        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_USER_INFORMATIONS, I18nService.getDefaultLocale( ), model );

        return template.getHtml( );
	}

	@Override
	public String getComponentDescription( ) {
		return I18nService.getLocalizedString( DASHBOARD_PROPERTIES_TITLE, I18nService.getDefaultLocale( ) );
	}

	@Override
	public String getComponentId( ) {
		return strIdComponent;
	}

    /**
     * Search name.
     *
     * @param key the key
     * @return the map
     */
    private static Map<String, String> searchUserInformations( String user_id )
    {

        Map<String, String> map = CacheUserAttributeService.getCachedAttributes( user_id );

        return map;

    }

}