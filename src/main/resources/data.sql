insert into user (age,authority,nickname,phonenumber,profile_color,pw,status)
values (10,'user','admin','01011112222','blue','$2a$10$bzpH5IDdLWqCoGZ56dQkfeWjxw1EnNa1r.uxJEw0bsiZSK5ki1JbW',1);
insert into parent (age,authority,created_at,nickname,pw,status,updated_at,user_id)
values (37,'parent',current_timestamp ,'testP','$2a$10$2B7aAdQamQEuj9VZPCztzu8zEabouqdu4JAOVmBKV4S91VUX7lt1C',1,current_timestamp,1);
insert into user (age,authority,nickname,phonenumber,profile_color,pw,status)
values (1,'master','master','01000000000','master','$2a$10$oaCs77mRNME1zwj1052QA.HWM6ZV9sBWAkVl5abuGA10bF0e8DdIC',1);