# Disclaimer \ Дисклеймер
Данный проект является одним из моих проектов для практики. Использование его крайне нежелательно на данный момент 
нежелательно и несет за собой определенные риски (кто-то еще использует Hessian?). Он написан левой пяткой за два вечера под пару баночек хмельного:)

------------
This project is one of my practice projects. Using it is highly undesirable at the moment and carries certain risks (does anyone else use Hessian?). It is written with a left heel in two
evenings with a couple of jars of drunk :)

# О проекте
Существует такой rpc протокол - Hessian. Он довольно давно устарел, однако иногда он все таки встречается для 
взаимодействия служб между собой. Данный протокол предоставляет возможность вызова методов службы удаленно на основе 
общей интерфейсной части. Этот проект представляет собой универсальный Java клиент для выполнения запросов к службам 
на основе данного протокола

## Кратко про то, как это работает
 * Мы заполняем манифест и указываем, где их искать
 * Приложение читает манифест
 * Скачивает jar в локальный каталог по урлу в манифесте
 * Создает ClassLoader для загруженных jar файлов
 * Из манифеста дергаем интерфейсы служб и читаем рефлексией доступные методы с аргументами
 * После этого приложение готово принимать запросы доступных методов и исполнять методы по эндпоинту /invoke
 * Аргументы параметров дезериализуются из json, если необходимо (не работает для примитивов)
 * Возвращается сериализованный ответ от метода или ошибка с текстом

