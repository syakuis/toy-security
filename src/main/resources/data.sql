INSERT INTO oauth_client_details
(
	client_id,
	client_secret,
	resource_ids,
	scope,
	authorized_grant_types,
	web_server_redirect_uri,
	authorities,
	access_token_validity,
	refresh_token_validity,
	additional_information,
	autoapprove
)
VALUES
(
	'bar',
	'foo',
	null,
	'read,write',
	'authorization_code,password, implicit, refresh_token',
	null,
	null,
	36000,
	2592000,
	null,
	null
);