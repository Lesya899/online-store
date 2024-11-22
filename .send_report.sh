
REPORT_PATH="results.sarif"
if [ -f "$REPORT_PATH" ]; then
    curl -X POST -H "Content-Type: application/json" -d @"$REPORT_PATH" http://localhost:8080/api/report/
else
    echo "Отчет Gitleaks не найден."
fi
