wc -l * \
  | sed -e 's/Export_//' -e 's/.csv//' \
  | grep -v README \
  | grep -v count \
  > README.csv
