document.addEventListener('DOMContentLoaded', () => {
    const API = '/api/items';
    const listEl = document.getElementById('itemList');
    const form = document.getElementById('addForm');
    const input = document.getElementById('itemName');

    // 📋 Загрузка и отображение списка
    async function loadItems() {
        try {
            const res = await fetch(API);
            if (!res.ok) throw new Error('Ошибка загрузки');
            const items = await res.json();
            renderList(items);
        } catch (err) {
            console.error('Не удалось загрузить список:', err);
            listEl.innerHTML = '<li style="color:red">Ошибка загрузки данных</li>';
        }
    }

    // 🎨 Рендеринг списка (вынесено отдельно для чистоты)
    function renderList(items) {
        listEl.innerHTML = '';
        if (items.length === 0) {
            listEl.innerHTML = '<li style="color:gray">Список пуст</li>';
            return;
        }
        items.forEach(item => {
            const li = document.createElement('li');
            li.className = item.purchased ? 'purchased' : '';
            li.dataset.id = item.id; // удобно для тестов и отладки
            li.innerHTML = `
                <span>${escapeHtml(item.name)}</span>
                <div>
                    <button class="action toggle-btn" data-id="${item.id}">
                        ${item.purchased ? 'Вернуть' : 'Куплено'}
                    </button>
                    <button class="action delete-btn" data-id="${item.id}">Удалить</button>
                </div>
            `;
            listEl.appendChild(li);
        });
    }

    // 🔐 Защита от XSS: экранирование пользовательского ввода
    function escapeHtml(str) {
        const div = document.createElement('div');
        div.textContent = str;
        return div.innerHTML;
    }

    // ➕ Обработка добавления
    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        const name = input.value.trim();
        if (!name) return;

        try {
            const res = await fetch(API, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ name })
            });
            if (res.ok) {
                input.value = '';
                await loadItems(); // перезагружаем список
            } else {
                alert('Не удалось добавить товар');
            }
        } catch (err) {
            console.error('Ошибка при добавлении:', err);
        }
    });

    // 🗑️ Делегирование событий для кнопок (эффективнее, чем навешивать на каждую)
    listEl.addEventListener('click', async (e) => {
        const btn = e.target.closest('button');
        if (!btn) return;

        const id = btn.dataset.id;
        const isDelete = btn.classList.contains('delete-btn');
        const isToggle = btn.classList.contains('toggle-btn');

        try {
            if (isDelete) {
                await fetch(`${API}/${id}`, { method: 'DELETE' });
            } else if (isToggle) {
                await fetch(`${API}/${id}`, { method: 'PATCH' });
            }
            await loadItems();
        } catch (err) {
            console.error('Ошибка операции:', err);
        }
    });

    // 🚀 Инициализация
    loadItems();
});