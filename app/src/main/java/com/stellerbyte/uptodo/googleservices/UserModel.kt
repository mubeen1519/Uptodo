package com.stellerbyte.uptodo.googleservices

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Welcome(
    @SerialName("project_info")
    val projectInfo: ProjectInfo?,

    val client: List<Client>,

    @SerialName("configuration_version")
    val configurationVersion: String
)

@Serializable
data class ProjectInfo (
    @SerialName("project_number")
    val projectNumber: String,

    @SerialName("project_id")
    val projectID: String,

    @SerialName("storage_bucket")
    val storageBucket: String
)

@Serializable
data class Client (
    @SerialName("client_info")
    val clientInfo: ClientInfo,

    @SerialName("oauth_client")
    val oauthClient: List<OauthClient>,

    @SerialName("api_key")
    val apiKey: List<APIKey>,

    val services: Services
)

@Serializable
data class APIKey (
    @SerialName("current_key")
    val currentKey: String
)

@Serializable
data class ClientInfo (
    @SerialName("mobilesdk_app_id")
    val mobilesdkAppID: String,

    @SerialName("android_client_info")
    val androidClientInfo: AndroidClientInfo
)

@Serializable
data class AndroidClientInfo (
    @SerialName("package_name")
    val packageName: String
)

@Serializable
data class Services (
    @SerialName("appinvite_service")
    val appinviteService: AppinviteService
)

@Serializable
data class AppinviteService (
    @SerialName("other_platform_oauth_client")
    val otherPlatformOauthClient: List<OauthClient>
)
@Serializable
data class OauthClient (
    @SerialName("client_id")
    val clientID: String,

    @SerialName("client_type")
    val clientType: Long
)





