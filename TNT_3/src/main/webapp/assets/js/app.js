
(function () {
    'use strict';

    function openModal(modal) {
        if (!modal) return;
        modal.classList.add('show');
        document.body.classList.add('tnt-modal-open');
        var backdrop = document.createElement('div');
        backdrop.className = 'modal-backdrop-tnt';
        backdrop.setAttribute('data-for', modal.id || '');
        document.body.appendChild(backdrop);
    }

    function closeModal(modal) {
        if (!modal) return;
        modal.classList.remove('show');
        document.body.classList.remove('tnt-modal-open');
        document.querySelectorAll('.modal-backdrop-tnt').forEach(function (b) {
            b.remove();
        });
    }

    document.addEventListener('click', function (e) {
        var opener = e.target.closest('[data-bs-toggle="modal"]');
        if (opener) {
            var targetSel = opener.getAttribute('data-bs-target');
            if (targetSel) {
                openModal(document.querySelector(targetSel));
            }
            return;
        }

        var closer = e.target.closest('[data-bs-dismiss="modal"]');
        if (closer) {
            closeModal(closer.closest('.modal'));
            return;
        }

        // Click ra ngoài hộp thoại (trên nền mờ) sẽ đóng modal.
        if (e.target.classList.contains('modal') && e.target.classList.contains('show')) {
            closeModal(e.target);
        }
    });

    document.addEventListener('keydown', function (e) {
        if (e.key === 'Escape') {
            var openEl = document.querySelector('.modal.show');
            if (openEl) closeModal(openEl);
        }
    });
})();
