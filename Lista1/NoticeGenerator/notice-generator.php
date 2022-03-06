<?php

/**
 * Plugin Name: Notice Generator
 * Plugin URI: https://example.com/plugins/Notice Generator/
 * Description: Generate notices to your posts.
 * Version: 1.0
 * Requires at least: 5.0
 * Requires PHP: 7.2
 * Author: Klaudia Błażyczek, Monika Galińska, Justyna Małuszyńska
 * Author URI: https://darksource.pl/
 * License: GPL v2 or later
 * License URI: https://www.gnu.org/licenses/gpl-2.0.html
 */


function ng_admin_actions_register_menu()
{
    add_options_page("Notice generator", "Generate notice to your posts", "manage_options", "ng", "ng_admin_page");
}

function ng_admin_page()
{
    global $_POST;
    if (isset($_POST['ng_add_notice'])) {
        if ($_POST['ng_add_notice'] == 'Y') {
            # jest już notice w bazie
            if (get_option("ng_notices")) {
                $notice_context = $_POST['ng_new_notice'];
                $notices = get_option("ng_notices");
                array_push($notices, $notice_context);
            } else {
                $notice_context = $_POST['ng_new_notice'];
                $notices = array($notice_context);
            }
            update_option('ng_notices', $notices);
            echo '<div class="notice notice-success isdismissible"><p>Notice added.</p></div>';
        }
    }

    if (isset($_POST['ng_remove_notice'])) {
        if ($_POST['ng_remove_notice'] == 'Y') {
            $notices = get_option('ng_notices');
            $index_notice_to_delete = intval($_POST['remove_index']);
            unset($notices[$index_notice_to_delete]);
            $notices = array_values($notices);
            update_option('ng_notices', $notices);
            echo '<div class="notice notice-success isdismissible"><p>Notice deleted. </p></div>';
        }
    }

    $notices = get_option('ng_notices');
?>
    <div class="wrap">
        <h1>Add notice to your posts</h1>
        <form name="ng_form_add" method="post">
            <input type="hidden" name="ng_add_notice" value="Y">
            <p>Add notice</p>
            <p><textarea rows="10" cols="100" name="ng_new_notice"></textarea></p>
            <p class="submit"><input class="submit-button" type="submit" value="Add"></p>
        </form>

        <?php
        if (!(empty($notices))) {
        ?>
            <h1>Remove notice</h1>
            <form name="ng_form_remove" method="post">
                <input type="hidden" name="ng_remove_notice" value="Y">
                <p>Remove notice</p>
                <?php
                for ($i = 0; $i < count($notices); $i++) {
                ?>
                    <div class="my-wrpper">
                        <input type="radio" id="<?php echo $i ?>" name="remove_index" value="<?php echo $i ?>" />
                        <span><?php echo $notices[$i] ?></span>
                    </div>
                <?php
                }
                ?>

                <p class="submit"><input class="submit-button" type="submit" value="Remove"></p>
            </form>
        <?php
        }
        ?>

    </div>
<?php
}

function get_random_notice()
{
    $notices_arr = get_option('ng_notices');
    return $notices_arr[array_rand($notices_arr, 1)];
}

function add_before_content($content)
{
    $random_notice = get_random_notice();
    return $random_notice . $content;
}

function ng_register_styles()
{
    wp_register_style('ng_styles', plugins_url('/css/style.css', __FILE__));
    wp_enqueue_style('ng_styles');
}

add_filter('the_content', 'add_before_content');
add_action('admin_menu', 'ng_admin_actions_register_menu');
add_action('init', 'ng_register_styles');
