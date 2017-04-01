DROP TABLE IF EXISTS PERMISSIONS;
CREATE TABLE PERMISSIONS (
    PERMISSION_NAME VARCHAR(250),
    ROLE VARCHAR(100)
);

-- accounting
INSERT INTO PERMISSIONS (PERMISSION_NAME, ROLE) 
VALUES 
('ch.pproject.vms.shared.accounting.account.CreateAccountingYearPermission', 'accounting'),
('ch.pproject.vms.shared.accounting.account.CreateAccountPermission', 'accounting'),
('ch.pproject.vms.shared.accounting.account.ReadAccountingOutlinePermission', 'accounting'),
('ch.pproject.vms.shared.accounting.account.ReadAccountingYearPermission', 'accounting'),
('ch.pproject.vms.shared.accounting.account.ReadAccountPermission', 'accounting'),
('ch.pproject.vms.shared.accounting.account.UpdateAccountingYearPermission', 'accounting'),
('ch.pproject.vms.shared.accounting.account.UpdateAccountPermission', 'accounting'),
('ch.pproject.vms.shared.accounting.posting.CreatePostingPermission', 'accounting'),
('ch.pproject.vms.shared.accounting.posting.ReadPostingPermission', 'accounting'),
('ch.pproject.vms.shared.accounting.posting.UpdatePostingPermission', 'accounting');

-- administration
INSERT INTO PERMISSIONS (PERMISSION_NAME, ROLE) 
VALUES 
('ch.pproject.vms.shared.core.administration.CreateUserPermission', 'administration'),
('ch.pproject.vms.shared.core.administration.ReadAdministrationOutlinePermission', 'administration'),
('ch.pproject.vms.shared.core.administration.ReadUserPermission', 'administration'),
('ch.pproject.vms.shared.core.administration.UpdateUserPermission', 'administration'),
('ch.pproject.vms.shared.core.desktop.ReadToolsMenuPermission', 'administration');

-- activity
INSERT INTO PERMISSIONS (PERMISSION_NAME, ROLE) 
VALUES 
('ch.pproject.vms.shared.core.activity.ReadActivityOutlinePermission', 'activity');

-- members
INSERT INTO PERMISSIONS (PERMISSION_NAME, ROLE) 
VALUES 
('ch.pproject.vms.shared.core.person.CreatePersonPermission', 'members'),
('ch.pproject.vms.shared.core.person.CreateRolePermission', 'members'),
('ch.pproject.vms.shared.core.person.DeleteRolePermission', 'members'),
('ch.pproject.vms.shared.core.person.ReadMemberOutlinePermission', 'members'),
('ch.pproject.vms.shared.core.person.ReadPersonPermission', 'members'),
('ch.pproject.vms.shared.core.person.ReadRolePermission', 'members'),
('ch.pproject.vms.shared.core.person.UpdatePersonPermission', 'members'),
('ch.pproject.vms.shared.core.person.UpdateRolePermission', 'members'),
('ch.pproject.vms.shared.core.team.CreateTeamPermission', 'members'),
('ch.pproject.vms.shared.core.team.UpdateTeamPermission', 'members'),
('ch.pproject.vms.shared.core.team.ReadTeamPermission', 'members');


INSERT INTO USER (USER_NR, USERNAME, PASSWORD)
		VALUES (nextval('vmsseq'), 'admin', 'FUO1BKz3Wp3KWNchjFQD5A==.j4kB+sMiddUAeW+Cpg7JnfXKOxujbADZ+u35q8f6jkg4RyrQfWU5uoKHFoOQF01/7M4ykQmcTCWFP8or69SPCQ==');

INSERT INTO USER (USER_NR, USERNAME, PASSWORD)
		VALUES (nextval('vmsseq'), 'pluesst', 'J0eZzJMwiii6XlcPhH6cTw==.6eFyg8dquneMnh59PSrtGX6dCMJkDD66WAb9ZxKo5IkR99T1TDeDrZZ8j/ofqgnJA342b/B232vQMjWuuF/E6Q==');

INSERT INTO USER_PERMISSION (USER_NR, PERMISSION_NAME)
SELECT USER_NR, PERMISSION_NAME 
FROM USER, PERMISSIONS 
WHERE USERNAME = 'pluesst';

-- arnessonm:alibaba40
INSERT INTO USER (USER_NR, USERNAME, PASSWORD)
		VALUES (nextval('vmsseq'), 'arnessonm', 'cx/H0y8HkSC4u9ReGynrHQ==.ReKx3opOIfaOKARcKxUPw6DO9ZLmeUL5h2jHZTxi58UEs6MgMnIix+w+6Otg2wkdX1d0EeluQ17rId28kyjM1Q==');

INSERT INTO USER_PERMISSION (USER_NR, PERMISSION_NAME)
SELECT USER_NR, PERMISSION_NAME 
FROM USER, PERMISSIONS 
WHERE USERNAME = 'arnessonm'
AND ROLE IN ('members');

-- altorferm:aladdin13
INSERT INTO USER (USER_NR, USERNAME, PASSWORD)
		VALUES (nextval('vmsseq'), 'altorferm', 'vlpbret0yqLeJvFbSSICiA==.MOGi4CgAcVIcw1ram5eXUGJxl5dKedixdrf4zprLzmGh6dqDLYdnKjlKN5u7uJ5D4CF2NKX5d+/G8ZK0V7cZYw==');

INSERT INTO USER_PERMISSION (USER_NR, PERMISSION_NAME)
SELECT USER_NR, PERMISSION_NAME 
FROM USER, PERMISSIONS 
WHERE USERNAME = 'altorferm'
AND ROLE IN ('members');

-- altorferu:sindbad56
INSERT INTO USER (USER_NR, USERNAME, PASSWORD)
		VALUES (nextval('vmsseq'), 'altorferu', 'TrLJc0GZlDJVYIHHcG5Nnw==.d2GSSz5NKK95zpsg50i7QHw1QHnUbUogfOX0KFIul83SfoPsGl4qgUWpxwDcLL6qft/rUVWuTliiZUGJFdo4/A==');

INSERT INTO USER_PERMISSION (USER_NR, PERMISSION_NAME)
SELECT USER_NR, PERMISSION_NAME 
FROM USER, PERMISSIONS 
WHERE USERNAME = 'altorferu'
AND ROLE IN ('members');

DROP TABLE IF EXISTS PERMISSIONS;