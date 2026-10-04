import 'package:flutter/material.dart';
import 'package:webview_flutter/webview_flutter.dart';
import 'package:share_plus/share_plus.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  runApp(const BioAppFlutterWrapper());
}

/// Aplicación Principal Wrapper Flutter para Daniela Madelain Erazo Montenegro
class BioAppFlutterWrapper extends StatefulWidget {
  const BioAppFlutterWrapper({super.key});

  @override
  State<BioAppFlutterWrapper> createState() => _BioAppFlutterWrapperState();
}

class _BioAppFlutterWrapperState extends State<BioAppFlutterWrapper> {
  // Estado global del tema en Flutter (Oscuro por defecto)
  ThemeMode _themeMode = ThemeMode.dark;

  void _toggleTheme() {
    setState(() {
      _themeMode = _themeMode == ThemeMode.dark ? ThemeMode.light : ThemeMode.dark;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Daniela Erazo CV - UPEC',
      debugShowCheckedModeBanner: false,
      themeMode: _themeMode,
      // Tema Claro (Light Mode - Colores Tierra & Terracota)
      theme: ThemeData(
        useMaterial3: true,
        brightness: Brightness.light,
        colorSchemeSeed: const Color(0xFF9E4624),
        scaffoldBackgroundColor: const Color(0xFFFDFBF7),
        appBarTheme: const AppBarTheme(
          backgroundColor: Color(0xFFFAF6F0),
          foregroundColor: Color(0xFF2C221E),
          elevation: 1,
        ),
      ),
      // Tema Oscuro Cálido (Dark Earth & Terracotta - Asimilado al Modo Claro)
      darkTheme: ThemeData(
        useMaterial3: true,
        brightness: Brightness.dark,
        colorSchemeSeed: const Color(0xFFE07A5F),
        scaffoldBackgroundColor: const Color(0xFF181311),
        appBarTheme: const AppBarTheme(
          backgroundColor: Color(0xFF241C18),
          foregroundColor: Color(0xFFF5EEE8),
          elevation: 1,
        ),
      ),
      home: WebEmbedScreen(
        onToggleTheme: _toggleTheme,
        isDarkMode: _themeMode == ThemeMode.dark,
      ),
    );
  }
}

/// Pantalla Principal con WebView Embebido y Controles Nativos
class WebEmbedScreen extends StatefulWidget {
  final VoidCallback onToggleTheme;
  final bool isDarkMode;

  const WebEmbedScreen({
    super.key,
    required this.onToggleTheme,
    required this.isDarkMode,
  });

  @override
  State<WebEmbedScreen> createState() => _WebEmbedScreenState();
}

class _WebEmbedScreenState extends State<WebEmbedScreen> {
  late final WebViewController _webViewController;
  
  // OPCIÓN B: URL remota de GitHub Pages
  static const String _remoteUrl = 'https://[COMPLETAR: usuario_github].github.io/web-cv/';
  static const String _localAssetPath = 'assets/web/index.html';

  bool _isLocalSource = true; // True = Opción A (Local), False = Opción B (Remoto)
  int _loadingProgress = 0;
  bool _isLoading = true;
  bool _hasError = false;
  String _errorMessage = '';

  @override
  void initState() {
    super.initState();
    _initWebViewController();
  }

  @override
  void didUpdateWidget(covariant WebEmbedScreen oldWidget) {
    super.didUpdateWidget(oldWidget);
    if (oldWidget.isDarkMode != widget.isDarkMode) {
      _syncWebTheme();
    }
  }

  void _initWebViewController() {
    _webViewController = WebViewController()
      ..setJavaScriptMode(JavaScriptMode.unrestricted)
      ..setBackgroundColor(widget.isDarkMode ? const Color(0xFF181311) : const Color(0xFFFDFBF7))
      ..setNavigationDelegate(
        NavigationDelegate(
          onPageStarted: (String url) {
            setState(() {
              _isLoading = true;
              _hasError = false;
              _loadingProgress = 0;
            });
          },
          onProgress: (int progress) {
            setState(() {
              _loadingProgress = progress;
            });
          },
          onPageFinished: (String url) {
            setState(() {
              _isLoading = false;
              _loadingProgress = 100;
            });
            _syncWebTheme();
          },
          onWebResourceError: (WebResourceError error) {
            setState(() {
              _isLoading = false;
              _hasError = true;
              _errorMessage = error.description;
            });
          },
        ),
      );

    _loadCurrentSource();
  }

  void _loadCurrentSource() {
    setState(() {
      _hasError = false;
      _isLoading = true;
    });

    if (_isLocalSource) {
      _webViewController.loadFlutterAsset(_localAssetPath);
    } else {
      _webViewController.loadRequest(Uri.parse(_remoteUrl));
    }
  }

  Future<void> _syncWebTheme() async {
    final String themeName = widget.isDarkMode ? 'dark' : 'light';
    try {
      await _webViewController.runJavaScript("window.setTheme('$themeName');");
    } catch (e) {
      debugPrint("Error al sincronizar tema con JS: $e");
    }
  }

  void _toggleSource() {
    setState(() {
      _isLocalSource = !_isLocalSource;
    });
    _loadCurrentSource();

    final sourceName = _isLocalSource ? "Local (Opción A)" : "Remoto GitHub Pages (Opción B)";
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        content: Text("Cargando fuente: $sourceName"),
        duration: const Duration(seconds: 2),
      ),
    );
  }

  void _showContactDialog() {
    showModalBottomSheet(
      context: context,
      shape: const RoundedRectangleBorder(
        borderRadius: BorderRadius.vertical(top: Radius.circular(20)),
      ),
      builder: (ctx) => Container(
        padding: const EdgeInsets.all(24),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            const Text(
              "Contacto - Daniela Erazo",
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 6),
            const Text(
              "Celular: 0939484810",
              style: TextStyle(fontSize: 14, color: Colors.grey),
            ),
            const SizedBox(height: 20),
            ListTile(
              leading: const Icon(Icons.phone, color: Color(0xFF10B981)),
              title: const Text("Llamar al 0939484810"),
              subtitle: const Text("Llamada directa desde el celular"),
              onTap: () {
                Navigator.pop(ctx);
                _webViewController.loadRequest(Uri.parse('tel:0939484810'));
              },
            ),
            ListTile(
              leading: const Icon(Icons.chat_bubble, color: Color(0xFF25D366)),
              title: const Text("Enviar WhatsApp (0939484810)"),
              subtitle: const Text("Abrir chat directo en WhatsApp"),
              onTap: () {
                Navigator.pop(ctx);
                _webViewController.loadRequest(Uri.parse('https://wa.me/593939484810'));
              },
            ),
          ],
        ),
      ),
    );
  }

  void _shareProfile() {
    const String shareText = "¡Hola! Te comparto la Hoja de Vida Profesional de Daniela Madelain Erazo Montenegro:\n\n"
        "🎓 Estudiante de Ingeniería en Computación (7mo Semestre) • UPEC\n"
        "📱 Despliegue Móvil Embebido en Flutter & Web CV\n"
        "✉️ Contacto: erazodaniela1995@gmail.com\n"
        "📞 Teléfono: 0939484810";

    Share.share(shareText, subject: "Hoja de Vida - Daniela Madelain Erazo Montenegro");
  }

  @override
  Widget build(BuildContext context) {
    return PopScope(
      canPop: false,
      onPopInvokedWithResult: (didPop, result) async {
        if (didPop) return;

        if (await _webViewController.canGoBack()) {
          await _webViewController.goBack();
        } else {
          if (context.mounted) {
            Navigator.of(context).pop();
          }
        }
      },
      child: Scaffold(
        appBar: AppBar(
          title: Row(
            children: [
              const Icon(Icons.person_pin_rounded, color: Color(0xFF10B981), size: 24),
              const SizedBox(width: 8),
              Expanded(
                child: Text(
                  _isLocalSource ? "Daniela Erazo (Local)" : "Daniela Erazo (Remoto)",
                  style: const TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
                  overflow: TextOverflow.ellipsis,
                ),
              ),
            ],
          ),
          actions: [
            IconButton(
              icon: const Icon(Icons.phone, color: Color(0xFF10B981)),
              tooltip: "Contacto (0939484810)",
              onPressed: _showContactDialog,
            ),
            IconButton(
              icon: Icon(_isLocalSource ? Icons.cloud_off : Icons.cloud_queue),
              tooltip: _isLocalSource ? "Cambiar a Remoto (Opción B)" : "Cambiar a Local (Opción A)",
              onPressed: _toggleSource,
            ),
            IconButton(
              icon: const Icon(Icons.refresh),
              tooltip: "Recargar Página",
              onPressed: () => _webViewController.reload(),
            ),
            IconButton(
              icon: Icon(widget.isDarkMode ? Icons.wb_sunny : Icons.nightlight_round),
              tooltip: widget.isDarkMode ? "Cambiar a Tema Claro" : "Cambiar a Tema Oscuro",
              onPressed: () {
                widget.onToggleTheme();
              },
            ),
            IconButton(
              icon: const Icon(Icons.share),
              tooltip: "Compartir Perfil",
              onPressed: _shareProfile,
            ),
          ],
        ),
        body: Column(
          children: [
            if (_isLoading)
              LinearProgressIndicator(
                value: _loadingProgress > 0 ? _loadingProgress / 100.0 : null,
                backgroundColor: Colors.transparent,
                color: const Color(0xFF6366F1),
                minHeight: 3,
              ),
            Expanded(
              child: _hasError
                  ? _buildErrorScreen()
                  : WebViewWidget(controller: _webViewController),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildErrorScreen() {
    return Center(
      child: Padding(
        padding: const EdgeInsets.all(24.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Icon(Icons.wifi_off_rounded, size: 64, color: Colors.redAccent),
            const SizedBox(height: 16),
            const Text(
              "Error al cargar la Hoja de Vida",
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 8),
            Text(
              _isLocalSource
                  ? "No se pudo cargar el archivo asset local."
                  : "No se pudo conectar a la URL remota de GitHub Pages.\nVerifica tu conexión a Internet.",
              textAlign: TextAlign.center,
              style: const TextStyle(fontSize: 13, color: Colors.grey),
            ),
            if (_errorMessage.isNotEmpty) ...[
              const SizedBox(height: 8),
              Text(
                "Detalle: $_errorMessage",
                style: const TextStyle(fontSize: 11, color: Colors.red),
                textAlign: TextAlign.center,
              ),
            ],
            const SizedBox(height: 24),
            ElevatedButton.icon(
              onPressed: _loadCurrentSource,
              icon: const Icon(Icons.replay),
              label: const Text("Reintentar Carga"),
              style: ElevatedButton.styleFrom(
                backgroundColor: const Color(0xFF6366F1),
                foregroundColor: Colors.white,
                padding: const EdgeInsets.horizontal(24, vertical: 12),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
