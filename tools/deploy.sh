npm run build
tar -cvf ../seproj.tar -C ../dist .
scp -v ../seproj.tar seproj@123.249.103.199:/var/www/seproj/seproj.tar
ssh seproj@123.249.103.199 'bash -s' << 'ENDSSH'
echo 'Unzipping'
cd /var/www/seproj
rm -rf seproj/*
tar -xvf seproj.tar -C seproj
exit
ENDSSH