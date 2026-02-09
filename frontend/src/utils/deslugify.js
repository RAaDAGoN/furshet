export function deslugify(slug) {
    const reverseTranslitMap = {
        'a': 'а', 'b': 'б', 'v': 'в', 'g': 'г', 'd': 'д', 'e': 'е', 'yo': 'ё',
        'zh': 'ж', 'z': 'з', 'i': 'и', 'y': 'й', 'k': 'к', 'l': 'л', 'm': 'м',
        'n': 'н', 'o': 'о', 'p': 'п', 'r': 'р', 's': 'с', 't': 'т', 'u': 'у',
        'f': 'ф', 'h': 'х', 'ts': 'ц', 'ch': 'ч', 'sh': 'ш', 'sch': 'щ',
        'yu': 'ю', 'ya': 'я'
    };

    // Сначала заменяем дефисы на пробелы
    let text = slug.replace(/-/g, ' ');

    // Заменяем транслитерации (обрабатываем самые длинные последовательности первыми)
    const patterns = Object.keys(reverseTranslitMap)
        .sort((a, b) => b.length - a.length); // Сортируем по длине в убывающем порядке

    patterns.forEach(pattern => {
        const regex = new RegExp(pattern, 'g');
        text = text.replace(regex, reverseTranslitMap[pattern]);
    });

    // Делаем первую букву заглавной для каждого слова
    text = text
        .split(' ')
        .map(word => word.charAt(0).toUpperCase() + word.slice(1))
        .join(' ');

    return text;
}