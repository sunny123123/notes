
+ 登录

sqlplus APS/APSTEST@//localhost:1521/xe

+ 显示中文乱码 

export NLS_LANG=AMERICAN_AMERICA.UTF8 然后再sqlplus。
如果这个不行，试试
export NLS_LANG='SIMPLIFIED CHINESE_CHINA.ZHS16GBK' 然后再sqlplus
