mkdir grep jdbc twitter
touch README.md
# inti README.md
cat > README.md << EOF
# Core Java Apps
This project is under development. Since this project follows the GitFlow, the final work will be merged to the master branch after Team Code Team.

1. [Java Grep App](./grep)
2. [JDBC App](./jdbc)
3. [Twitter CLI App](./twitter)
EOF

#create empty tmp file for teach project
#each project follows maven standard dir layout https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html
for dir in $(ls -d */); do
touch $dir/pom.xml;
touch $dir/README.md;
mkdir -p $dir/src/test/java/ca/jrvs/apps/$dir
mkdir -p $dir/src/main/java/ca/jrvs/apps/$dir
done
