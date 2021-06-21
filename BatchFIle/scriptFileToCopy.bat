set srcDir=\\dxbegww116dv\share\Parantap\xbaglogs\aws\aws
set destdir=C:\Parantap\personal\Project\testCopy\batchScript
pushd %srcDir%
for /f "tokens=*" %%G in ('dir *.* /b /a-d /od') do set lastmod=%%G
copy "%lastmod%" "%destDir%"
cmd /k