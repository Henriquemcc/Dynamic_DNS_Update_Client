jpackage \
  --input ../../app/build/libs \
  --name "Dynamic DNS Update Client" \
  --main-jar app.jar \
  --main-class io.github.henriquemcc.dduc.AppKt \
  --add-launcher dduc=launcher.properties \
  --type dmg \
  --dest build