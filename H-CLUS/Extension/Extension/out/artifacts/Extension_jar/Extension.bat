@ECHO OFF
echo Running batch file...

:: Verifica il percorso relativo
echo Script directory: %~dp0

:: Percorso completo per i moduli JavaFX
set FX_MODULE_PATH=%~dp0javafx-sdk-22.0.2\lib
echo JavaFX module path: %FX_MODULE_PATH%

:: Verifica che il percorso esista
if not exist "%FX_MODULE_PATH%" (
    echo JavaFX module path does not exist!
    pause
    exit /b 1
)

:: Esegui l'applicazione Java con percorsi di debug
java --module-path "%FX_MODULE_PATH%" --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.base,javafx.media,javafx.swing,javafx.web -jar %~dp0Extension.jar %*

:: Verifica il codice di errore
if %errorlevel% neq 0 (
    echo Application failed with error code %errorlevel%
)
pause
